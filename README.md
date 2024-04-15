# phive-binary

[![javadoc](https://javadoc.io/badge2/com.helger.phive/phive-binary/javadoc.svg)](https://javadoc.io/doc/com.helger.phive/phive-binary)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.helger.phive/phive-binary/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.helger.phive/phive-binary) 

A validation library for binary formats inspired by phive.
The goal of the library is to make sure, that certain binary file types can be checked for validity based on either file extension or MIME Type.

# Basic usage

Each file format is implemented as an instance of `IFileFormatDescriptor`.
Each file format is registered into the singleton instance `FileFormatRegistry.getInstance ()`.
Based on a file extension (e.g. `pdf`) or based on a MIME type (e.g. `application/pdf`) you need to get the `IFileFormatDescriptor` you need.
And for each file format descriptor several content validators may be available.
Via `getContentValidatorFavourSpeed ()` or `getContentValidatorFavourAccuracy ()` the matching `IPhiveContentValidator` can be retrieved.
And finally the content validator does the actual matching.

So to summarize:
1. Get `IFileFormatDescriptor` from `FileFormatRegistry.getInstance ()`
1. Choose the `IPhiveContentValidator` from the file format descriptor (this object can be cached by the way) 
1. Let the content validator check if the provided value matches

# Extensibility

All known file formats are loaded via [SPI](https://docs.oracle.com/javase/tutorial/ext/basics/spi.html).
By implementing the `com.helger.phive.binary.IFileFormatRegistrarSPI` in your application, custom file formats can be added.

# Maven usage

Add the following to your pom.xml to use this artifact, replacing `x.y.z` with the real version number:

```xml
<dependency>
  <groupId>com.helger.phive</groupId>
  <artifactId>phive-binary</artifactId>
  <version>x.y.z</version>
</dependency>
```

# News and Noteworthy

* v0.1.0 - 2024-01-31
    * Initial version
    * Supported formats are: CSV (no content validator), GIF, JPG, PDF, PNG, PSD, TIFF, XLS, XSLX and XML
