/**
 * The MTS support core project contains client related utilities, data transfer objects and remote EJB interfaces for communication with the CDS Framework Middle Tier Service.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see <http://www.gnu.org/licenses/> for more
 * details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the New York City
 * Department of Health and Mental Hygiene, Bureau of Immunization to have (without restriction,
 * limitation, and warranty) complete irrevocable access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; THE
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.xml.bind.annotation.XmlRootElement;
import org.cdsframework.base.BaseDTO;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.util.support.CoreConstants;

/**
 * Provides class utility functions.
 *
 * @author HLN Consulting, LLC
 */
public class ClassUtils {

    private final static LogUtils logger = LogUtils.getLogger(ClassUtils.class);
    private final static String sep = System.getProperty("file.separator");
    private final static List<String> DECLARED_FIELD_IGNORE_PATTERNS = Arrays.asList(new String[]{"ajc$tjp", "$switch_table", "serialversionuid"});

    private static Map<String, Class<? extends BaseDTO>> dtoClassMap = null;
    
    /**
     * Returns a class from a string.
     *
     * @param className
     * @return
     * @throws NotFoundException
     */
    public static Class classForName(String className) throws NotFoundException {
        final String METHODNAME = "classForName ";
        if (className == null) {
            throw new NotFoundException(METHODNAME + "className is null!");
        }
        String newClassName = className.replace("_", "$");
        if (!newClassName.contains(".")) {
            newClassName = CoreConstants.QUERY_CLASS_PKG_PATH + newClassName;
        }
        try {
            return Class.forName(newClassName);
        } catch (ClassNotFoundException e) {
            String msg = METHODNAME + "Class name not found: " + newClassName;
            logger.error(msg);
            throw new NotFoundException(msg);
        }
    }
    
    private static final Map<String, Class> dtoQueryClassMap = new HashMap<String, Class>();
    

    /**
     * Returns a class from a string.
     *
     * @param baseDTO
     * @param queryClassName
     * @return
     * @throws NotFoundException
     */
    public static Class dtoClassForName(BaseDTO baseDTO, String queryClassName) throws NotFoundException {
        final String METHODNAME = "dtoClassForName ";
        if (baseDTO == null) {
            throw new NotFoundException(METHODNAME + "baseDTO is null!");
        }
        return dtoClassForName(baseDTO.getClass(), queryClassName);
    }

    /**
     * Returns a class from a string.
     *
     * @param dtoClass
     * @param queryClassName
     * @return
     * @throws NotFoundException
     */
    public static Class dtoClassForName(Class<? extends BaseDTO> dtoClass, String queryClassName) throws NotFoundException {
        final String METHODNAME = "dtoClassForName ";
        try {
            if (dtoClass == null) {
                throw new NotFoundException(METHODNAME + "dtoClass is null!");
            }
            String queryClassKey = dtoClass.getName() + "$" + queryClassName;
            Class queryClass = dtoQueryClassMap.get(queryClassKey);
            if (queryClass == null) {
                queryClass = Class.forName(queryClassKey);
                dtoQueryClassMap.put(queryClassKey, queryClass);
            }
            return queryClass;
        } catch (ClassNotFoundException e) {
            try {
                return classForName(queryClassName);
            } catch (NotFoundException ex) {
                String msg = METHODNAME + "Class name not found: " + dtoClass.getName() + "$" + queryClassName + " or " + CoreConstants.QUERY_CLASS_PKG_PATH + queryClassName;
                logger.error(msg);
                throw new NotFoundException(msg);
            }
        }
    }

    /**
     * Get the underlying class for a type, or null if the type is a variable type.
     *
     * @param type the type
     * @return the underlying class
     */
    public static Class getTypeClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getTypeClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class componentClass = getTypeClass(componentType);
            if (componentClass != null) {
                return (Class) Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get the actual type arguments a child class has used to extend a generic base class.
     *
     * @param <T>
     * @param baseClass the base class
     * @param childClass the child class
     * @return the first raw class for the actual type arguments.
     */
    public static <T> Class getTypeArgument(Class<T> baseClass, Class<? extends T> childClass) {
        return getTypeArguments(baseClass, childClass).get(0);
    }

    /**
     * Get the actual type arguments a child class has used to extend a generic base class.
     *
     * @param <T>
     * @param baseClass the base class
     * @param childClass the child class
     * @return the first raw class for the actual type arguments.
     */
    public static <T> List<Class> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit baseClass
        while (!getTypeClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just keep going.
                type = ((Class) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        // finally, for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class> typeArgumentsAsClasses = new ArrayList<Class>();
        // resolve types by chasing down type variables.
        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getTypeClass(baseType));
        }
        return typeArgumentsAsClasses;
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static Class[] getClassesFromClasspath(String packageName) throws IOException, ClassNotFoundException, URISyntaxException {
        final String METHODNAME = "getClassesFromClasspath ";
        String path = packageName.replace('.', '/');
        ClassLoader cloader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = cloader.getResources(path);
        ArrayList<Class> classes = new ArrayList<Class>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource != null) {
                String resourcePath = resource.getPath().split("!")[0].split("\\.jar")[0] + ".jar";
                if (resourcePath.startsWith("file:")) {
                    resourcePath = resourcePath.split("file:")[1];
                }
                logger.debug(METHODNAME, "resourcePath=", resourcePath);
                File file = new File(resourcePath);
                JarFile jarFile = new JarFile(file);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry nextJarEntry = entries.nextElement();
                    String name = nextJarEntry.getName();
                    if (name.startsWith(path) && name.endsWith(".class") && !name.endsWith("package-info.class") && !name.contains("$")) {
                        name = name.replace("/", ".").substring(0, name.length() - 6);
                        classes.add(Class.forName(name));
                    }
                }
            }
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws Exception
     */
    public static Class[] getClasses(String packageName) throws Exception {
        List<Class> classes = new ArrayList<Class>();
        String path = packageName.replace('.', '/');
        ClassLoader cloader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = cloader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            //System.out.println("Getting DTO classes from: " + resource.getPath());
            getDtoClasses(new File(resource.getPath()), classes);
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     *
     * @param aFile
     * @param classes
     * @throws Exception
     */
    public static void getDtoClasses(File aFile, List<Class> classes) throws Exception {
        if (aFile.isFile()) {
            Class<?> clazz = Class.forName(aFile.getPath().split("classes\\" + sep)[1].split(".class")[0].replace(sep, "."));
            if (!classes.contains(clazz)) {
                classes.add(clazz);
            }
        } else if (aFile.isDirectory()) {
            File[] listOfFiles = aFile.listFiles();
            if (listOfFiles != null) {
                for (File listOfFile : listOfFiles) {
                    getDtoClasses(listOfFile, classes);
                }
            }
        }
    }

    /**
     * Return a list of declared fields from a class and any of its super classes. BaseDTO declared fields are excluded. Fields
     * matching the DECLARED_FIELD_IGNORE_PATTERNS list are ignored as well.
     *
     * @param klass
     * @return
     */
    public static List<Field> getNonBaseDTODeclaredFields(Class<?> klass) {
//        final String METHODNAME = "getNonBaseDTODeclaredFields ";
        List<Field> result = new ArrayList<Field>();

        Class<?> superclass = klass;
        while (superclass != null) {
            for (Field item : superclass.getDeclaredFields()) {
                if (item.getDeclaringClass() != BaseDTO.class && isDeclaredFieldNameValid(item.getName())) {
                    item.setAccessible(true);
                    result.add(item);
                }
            }
            superclass = superclass.getSuperclass();
        }
//        logger.info(METHODNAME, "result=", result);
        return result;
    }

    /**
     * Determine if the field name matches an ignored pattern. If it does return false. Also returns false if the fieldName is null.
     *
     * @param fieldName
     * @return
     */
    private static boolean isDeclaredFieldNameValid(String fieldName) {
        boolean result = true;
        if (fieldName != null) {
            for (String item : DECLARED_FIELD_IGNORE_PATTERNS) {
                if (fieldName.toLowerCase().contains(item)) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Return the declared field from a class or any of its super classes.
     *
     * @param klass
     * @param fieldName
     * @return
     * @throws java.lang.NoSuchFieldException
     */
    public static Field getDeclaredField(Class<?> klass, String fieldName) throws NoSuchFieldException {
        Field result = null;

        Class<?> superclass = klass;
        while (superclass != null) {
            for (Field item : superclass.getDeclaredFields()) {
                if (item.getDeclaringClass() != BaseDTO.class) {
                    if (item.getName().equals(fieldName)) {
                        result = item;
                        break;
                    }
                }
            }
            superclass = superclass.getSuperclass();
        }
        if (result == null) {
            throw new NoSuchFieldException(fieldName + " not found!");
        } else {
            result.setAccessible(true);
        }
        return result;
    }
//    public static String getResourceType(Class<? extends BaseDTO> resource) {
//        // Start off with canonical name
//        String resourceType = resource.getCanonicalName();
//        // Check XmlRootElement
//        String xmlRootElementName = getXmlRootElementName(resource);
//        if (xmlRootElementName != null) {
//            resourceType = xmlRootElementName;
//        }
//        return resourceType;
//    }
    public static String getResourceName(Class resource) {
        final String METHODNAME = "getResourceName ";
        // Start with simpleName
        String resourceName = resource.getSimpleName();

        // Get XmlRootElement
        String xmlRootElementName = getXmlRootElementName(resource);
        if (xmlRootElementName != null) {
            resourceName = xmlRootElementName;
        }
        
        // Check DTO pos
        int dtoPos = resourceName.toUpperCase().indexOf("DTO");
        if (dtoPos >= 0) {
            // Is there an Lk before the DTO ?
            int lkPos = resourceName.toUpperCase().indexOf("LKDTO");
            if (lkPos >= 0) {
                dtoPos = lkPos;
            }
            resourceName = resourceName.substring(0, dtoPos);
        }

        // Pluralize, if ends in 's' add 'es'
        String pluralizedEnding = "s";
        if (resourceName.endsWith("s")) {
            pluralizedEnding = "es";
        } else if (resourceName.endsWith("y")) {
            resourceName = resourceName.substring(0, resourceName.length()-1);
            pluralizedEnding = "ies";
        }
        resourceName = resourceName.toLowerCase() + pluralizedEnding;
        
//        logger.info(METHODNAME, "resourceName=", resourceName);
        return resourceName;
    }
    
    public static String getXmlRootElementName(Class resource) {
        final String METHODNAME = "getXmlRootElementName ";
        String resourceName = null;
        XmlRootElement xmlRootElement = (XmlRootElement) resource.getAnnotation(XmlRootElement.class);
        if (xmlRootElement != null) {
            String xmlRootElementName = xmlRootElement.name();
            if (xmlRootElementName != null) {
                if (xmlRootElementName.indexOf("##") >= 0) {
//                    logger.error(METHODNAME, "There is XmlRootElement without a name, ", xmlRootElementName, 
//                            " see class: ", resource.getCanonicalName(), " you should assign a XmlRootElementName with the annotation,",
//                            " will use default implementation ");
                }
                else {
                    resourceName = xmlRootElementName;
                }
            }
        }
//        logger.info(METHODNAME, "resourceName=", resourceName);
        return resourceName;
        
    }
    public static Map<String, Class<? extends BaseDTO>> getDtoClassMap() throws IOException, ClassNotFoundException, URISyntaxException {
        final String METHODNAME = "getDtoClassMap ";
        
        if (dtoClassMap == null) {
            dtoClassMap = new HashMap<String, Class<? extends BaseDTO>>();
            Class[] classes = getClassesFromClasspath("org.cdsframework.dto");
            for (Class cls : classes) {
                if (BaseDTO.class.isAssignableFrom(cls)) {
                    logger.info(METHODNAME, "cls.getSimpleName()=", cls.getSimpleName(), " cls.getCanonicalName()=", cls.getCanonicalName());
                    String key = cls.getSimpleName();
                    if (!dtoClassMap.containsKey(key)) {
                        dtoClassMap.put(key, cls);
                    }
                    else {
                        logger.error(METHODNAME, "The class SimpleName " + key + " already exists");
                    }
                    key = cls.getCanonicalName();
                    if (!dtoClassMap.containsKey(key)) {
                        dtoClassMap.put(key, cls);
                    }
                    else {
                        logger.error(METHODNAME, "The class CanonicalName " + key + " already exists");
                    }
                    
                    // Get XmlRootElement
                    key = getXmlRootElementName(cls);
                    if (key != null) {
                        if (!dtoClassMap.containsKey(key)) {
                            dtoClassMap.put(key, cls);
                        }
                        else {
                            logger.error(METHODNAME, "The class xmlRootElement name " + key + " already exists");
                        }
                    }

                    // Pluralized resource
                    key = ClassUtils.getResourceName(cls);
                    if (!dtoClassMap.containsKey(key)) {
                        dtoClassMap.put(key, cls);
                    }
                    else {
                        logger.error(METHODNAME, "The class resource name " + key + " already exists");
                    }
                    
                } else {
                    logger.warn("Found a class in the org.cdsframework.dto package that does not extend BaseDTO: ", cls.getSimpleName());
                }
            }
        }
        return dtoClassMap;
    }

}
