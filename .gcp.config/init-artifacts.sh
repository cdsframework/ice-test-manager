#!/bin/bash

mvn install:install-file -Dfile=artifacts/opencds-parent-2.0.4.pom -DgroupId=org.opencds -DartifactId=opencds-parent -Dversion=2.0.4 -Dpackaging=pom
mvn install:install-file -Dfile=artifacts/opencds-config-2.0.4.pom -DgroupId=org.opencds -DartifactId=opencds-config -Dversion=2.0.4 -Dpackaging=pom
mvn install:install-file -Dfile=artifacts/opencds-config-schema-2.0.4.jar -DpomFile=artifacts/opencds-config-schema-2.0.4.pom
mvn install:install-file -Dfile=artifacts/opencds-vmr-1_0-schema-2.0.4.jar -DpomFile=artifacts/opencds-vmr-1_0-schema-2.0.4.pom
mvn install:install-file -Dfile=artifacts/sunny-1.0.9.jar -DpomFile=artifacts/sunny-1.0.9.pom
mvn install:install-file -Dfile=artifacts/redmond-1.0.10.jar -DpomFile=artifacts/redmond-1.0.10.pom
mvn install:install-file -Dfile=artifacts/themes-project-1.0.9.pom -DgroupId=org.primefaces.themes -DartifactId=themes-project -Dversion=1.0.9 -Dpackaging=pom
mvn install:install-file -Dfile=artifacts/themes-project-1.0.10.pom -DgroupId=org.primefaces.themes -DartifactId=themes-project -Dversion=1.0.10 -Dpackaging=pom
