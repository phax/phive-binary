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

import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.mime.CMimeType;
import com.helger.phive.binary.AbstractFileFormatDescriptor;
import com.helger.phive.binary.EContentDetectionMode;
import com.helger.phive.binary.IContentDetector;

/**
 * File format descriptor for PDF
 *
 * @author Philip Helger
 */
public class FileFormatDescriptorPDF extends AbstractFileFormatDescriptor
{
  private static final byte [] MIME_ID_PDF = { '%', 'P', 'D', 'F' };

  @Nonnull
  private static Map <EContentDetectionMode, IContentDetector> _getContentDetectors ()
  {
    final Map <EContentDetectionMode, IContentDetector> ret = new CommonsHashMap <> ();
    ret.put (EContentDetectionMode.LEADING_BYTES, data -> ArrayHelper.startsWith (data, MIME_ID_PDF));
    return ret;
  }

  public FileFormatDescriptorPDF ()
  {
    super ("Portable Document Format",
           "PDF",
           new CommonsArrayList <> ("pdf"),
           new CommonsArrayList <> (CMimeType.APPLICATION_PDF.getAsStringWithoutParameters ()),
           _getContentDetectors ());
  }
}
