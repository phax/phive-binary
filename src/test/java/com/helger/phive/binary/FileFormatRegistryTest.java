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
package com.helger.phive.binary;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.helger.commons.mime.CMimeType;
import com.helger.commons.mime.IMimeType;
import com.helger.phive.binary.impl.FileFormatDescriptorCSV;
import com.helger.phive.binary.impl.FileFormatDescriptorPDF;

/**
 * Test class for class {@link FileFormatRegistry}.
 *
 * @author Philip Helger
 */
public class FileFormatRegistryTest
{
  @Test
  public void testBasic ()
  {
    final FileFormatRegistry aReg = FileFormatRegistry.getInstance ();
    assertNull (aReg.getFileFormatDescriptorByFileExtension ("aaa"));
    assertNull (aReg.getFileFormatDescriptorByFileExtension (""));
    assertNull (aReg.getFileFormatDescriptorByFileExtension (null));
    assertNull (aReg.getFileFormatDescriptorByMimeType ("application/xyz"));
    assertNull (aReg.getFileFormatDescriptorByMimeType ("application/pdf; x=y"));
    assertNull (aReg.getFileFormatDescriptorByMimeType (""));
    assertNull (aReg.getFileFormatDescriptorByMimeType ((String) null));
    assertNull (aReg.getFileFormatDescriptorByMimeType ((IMimeType) null));
  }

  @Test
  public void testCSV ()
  {
    final FileFormatRegistry aReg = FileFormatRegistry.getInstance ();
    assertTrue (aReg.getAllFileFormatDescriptors ().containsAnyValue (FileFormatDescriptorCSV.class::isInstance));
    assertNotNull (aReg.getFileFormatDescriptorByFileExtension ("csv"));
    assertNotNull (aReg.getFileFormatDescriptorByMimeType (CMimeType.TEXT_CSV));
  }

  @Test
  public void testPDF ()
  {
    final FileFormatRegistry aReg = FileFormatRegistry.getInstance ();
    assertTrue (aReg.getAllFileFormatDescriptors ().containsAnyValue (FileFormatDescriptorPDF.class::isInstance));
    assertNotNull (aReg.getFileFormatDescriptorByFileExtension ("pdf"));
    assertNotNull (aReg.getFileFormatDescriptorByMimeType (CMimeType.APPLICATION_PDF));
  }

  @Test
  public void testDetermination ()
  {
    final byte [] aMatching = "%PDF-1.6blafoo".getBytes (StandardCharsets.ISO_8859_1);
    final byte [] aFailing = "%PdF-1.6blafoo".getBytes (StandardCharsets.ISO_8859_1);

    final FileFormatRegistry aReg = FileFormatRegistry.getInstance ();
    final IPhiveContentValidator aContentValidator = aReg.getFileFormatDescriptorByMimeType (CMimeType.APPLICATION_PDF)
                                                         .getContentValidatorFavourSpeed ();
    assertNotNull (aContentValidator);
    assertTrue (aContentValidator.matchesContent (aMatching));
    assertFalse (aContentValidator.matchesContent (aFailing));
  }
}
