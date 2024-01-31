/*
 * Copyright (C) 2024 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.phive.binary.impl;

import java.util.Map;

import javax.annotation.Nonnull;

import com.helger.commons.charset.EUnicodeBOM;
import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.mime.CMimeType;
import com.helger.phive.binary.AbstractFileFormatDescriptor;
import com.helger.phive.binary.EPhiveContentValidationMode;
import com.helger.phive.binary.IPhiveContentValidator;

/**
 * File format descriptor for XML documents
 *
 * @author Philip Helger
 */
public class FileFormatDescriptorXML extends AbstractFileFormatDescriptor
{
  private static final ICommonsList <byte []> PREFIXES = new CommonsArrayList <> (8 * EUnicodeBOM.values ().length);
  static
  {
    // Add all XML mime types: as the combination of all BOMs and all character
    // encodings as determined by
    // http://www.w3.org/TR/REC-xml/#sec-guessing
    final ICommonsList <byte []> aXMLStuff = new CommonsArrayList <> ();
    // UCS4
    aXMLStuff.add (new byte [] { 0x3c, 0x00, 0x00, 0x00, 0x3f, 0x00, 0x00, 0x00 });
    aXMLStuff.add (new byte [] { 0x00, 0x3c, 0x00, 0x00, 0x00, 0x3f, 0x00, 0x00 });
    aXMLStuff.add (new byte [] { 0x00, 0x00, 0x3c, 0x00, 0x00, 0x00, 0x3f, 0x00 });
    aXMLStuff.add (new byte [] { 0x00, 0x00, 0x00, 0x3c, 0x00, 0x00, 0x00, 0x3f });
    // UTF-16
    aXMLStuff.add (new byte [] { 0x00, 0x3c, 0x00, 0x3f });
    aXMLStuff.add (new byte [] { 0x3c, 0x00, 0x3f, 0x00 });
    // ISO-8859-1/UTF-8/ASCII etc.
    aXMLStuff.add (new byte [] { 0x3c, 0x3f, 0x78, 0x6d });
    // EBCDIC
    aXMLStuff.add (new byte [] { 0x4c, 0x6f, (byte) 0xa7, (byte) 0x94 });

    // Remember all types without the BOM
    PREFIXES.addAll (aXMLStuff);

    // Remember all types with each BOM
    for (final EUnicodeBOM eBOM : EUnicodeBOM.values ())
      for (final byte [] aXML : aXMLStuff)
      {
        final byte [] aData = ArrayHelper.getConcatenated (eBOM.getAllBytes (), aXML);
        PREFIXES.add (aData);
      }
  }

  @Nonnull
  private static Map <EPhiveContentValidationMode, IPhiveContentValidator> _getContentValidators ()
  {
    final Map <EPhiveContentValidationMode, IPhiveContentValidator> ret = new CommonsHashMap <> ();
    ret.put (EPhiveContentValidationMode.LEADING_BYTES, data -> {
      for (final byte [] aPrefix : PREFIXES)
        if (ArrayHelper.startsWith (data, aPrefix))
          return true;
      return false;
    });
    return ret;
  }

  public FileFormatDescriptorXML ()
  {
    super ("XML",
           "XML",
           new CommonsArrayList <> ("xml"),
           new CommonsArrayList <> (CMimeType.APPLICATION_XML.getAsString (), CMimeType.TEXT_XML.getAsString ()),
           _getContentValidators ());
  }
}
