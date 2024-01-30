# phive-binary

A validation library for binary formats inspired by phive.
The goal of the library is to make sure, that certain binary file types can be checked for validity based on either file extension or MIME Type.

# Basic usage

Each file format is implemented as an instance of `IFileFormatDescriptor`.
Each file format is registered into the singleton instance `FileFormatRegistry.getInstance ()`.
Based on a file extension (e.g. `pdf`) or based on a MIME type (e.g. `application/pdf`) you need to get the `IFileFormatDescriptor` you need.
And for each file format descriptor several content determinators may be available.

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

* v0.1.0 - 2024-01-30
    * Initial version
