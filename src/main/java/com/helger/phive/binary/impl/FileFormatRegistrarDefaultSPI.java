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

import javax.annotation.Nonnull;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.phive.binary.IFileFormatRegistrarSPI;
import com.helger.phive.binary.IFileFormatRegistry;

/**
 * The default implementation of {@link IFileFormatRegistrarSPI}
 *
 * @author Philip Helger
 */
@IsSPIImplementation
public final class FileFormatRegistrarDefaultSPI implements IFileFormatRegistrarSPI
{
  public void registerFileFormats (@Nonnull final IFileFormatRegistry aRegistry)
  {
    aRegistry.registerFileFormat (new FileFormatDescriptorCSV ());
    aRegistry.registerFileFormat (new FileFormatDescriptorGIF ());
    aRegistry.registerFileFormat (new FileFormatDescriptorPDF ());
    aRegistry.registerFileFormat (new FileFormatDescriptorPNG ());
    aRegistry.registerFileFormat (new FileFormatDescriptorPSD ());
    aRegistry.registerFileFormat (new FileFormatDescriptorTIFF ());
    aRegistry.registerFileFormat (new FileFormatDescriptorXLS ());
    aRegistry.registerFileFormat (new FileFormatDescriptorXLSX ());
  }
}
