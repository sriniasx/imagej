/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2012 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package imagej.core.plugins.convolve;

import imagej.data.Dataset;
import imagej.data.display.ImageDisplay;
import imagej.data.display.ImageDisplayService;
import imagej.data.display.OverlayService;
import imagej.ext.plugin.Menu;
import imagej.ext.plugin.Parameter;
import imagej.ext.plugin.Plugin;
import imagej.menu.MenuConstants;
import imagej.module.ItemIO;
import imagej.plugin.ContextPlugin;
import imagej.util.RealRect;

/**
 * Implements IJ1's Smooth plugin functionality
 * 
 * @author Barry DeZonia
 */
@Plugin(menu = {
	@Menu(label = MenuConstants.PROCESS_LABEL,
		weight = MenuConstants.PROCESS_WEIGHT,
		mnemonic = MenuConstants.PROCESS_MNEMONIC),
	@Menu(label = "Smooth", weight = 1, accelerator = "shift control S") },
	headless = true)
public class SmoothDataValues extends ContextPlugin {

	// -- instance variables that are Parameters --

	@Parameter
	private ImageDisplayService imageDisplayService;

	@Parameter
	private OverlayService overlayService;

	@Parameter(type = ItemIO.BOTH)
	private ImageDisplay display;

	// -- public interface --

	@Override
	public void run() {
		final Dataset input = imageDisplayService.getActiveDataset(display);
		final RealRect selection = overlayService.getSelectionBounds(display);
		final double[] kernel = { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		final Convolve3x3Operation operation =
			new Convolve3x3Operation(input, selection, kernel);
		operation.run();
	}

	public void setDisplay(ImageDisplay disp) {
		display = disp;
	}
	
	public ImageDisplay getDisplay() {
		return display;
	}
}
