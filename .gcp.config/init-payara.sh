create-jdbc-connection-pool --restype "${MTS_DATA_SOURCE_RES_TYPE}" --datasourceclassname "${MTS_DATA_SOURCE_CLASS_NAME}" --validationclassname "org.glassfish.api.jdbc.validation.PostgresConnectionValidation" --isconnectionvalidationrequired "true" --connectionvalidationmethod "custom-validation" --property "${MTS_DATA_SOURCE_PROPERTIES}" mts
create-jdbc-resource --connectionpoolid mts jdbc/mts
create-jdbc-connection-pool --restype "${CDS_DATA_SOURCE_RES_TYPE}" --datasourceclassname "${CDS_DATA_SOURCE_CLASS_NAME}" --validationclassname "org.glassfish.api.jdbc.validation.PostgresConnectionValidation" --isconnectionvalidationrequired "true" --connectionvalidationmethod "custom-validation" --property "${CDS_DATA_SOURCE_PROPERTIES}" cds
create-jdbc-resource --connectionpoolid cds jdbc/cds
create-jvm-options '-verbose:gc'
create-jvm-options '-XX:+PrintGCDetails'
create-jvm-options '-XX:+PrintGCTimeStamps'