# karera-drf
Java-based parser for Brisnet Thoroughbred Past Performances data file (single-file format).

Data files can be purchased and downloaded from https://www.brisnet.com/product/data-files/DRS. This parser maps data from data file to Java domain objects which can then be easily used within your application. Domain classes (e.g. DrfRace, DrfHorse, etc.) are generated at build time from Avro IDL files (https://avro.apache.org/docs/1.8.2/idl.html).

Build:
```bash
mvn clean install
```

Code Example:
```java
SingleFileDrf drf = SingleFileDrfParser.parse("AQU0101.DRF");
List<DrfRace> races = drf.getRaces();
```
The **parse** can accept arguments of type String (file path), File, Reader, and InputStream.
