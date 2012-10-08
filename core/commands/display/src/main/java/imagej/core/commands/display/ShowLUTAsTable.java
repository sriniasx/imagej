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

package imagej.core.commands.display;

import java.util.List;

import net.imglib2.display.ColorTable;

import imagej.command.ContextCommand;
import imagej.data.Position;
import imagej.data.display.DatasetView;
import imagej.data.display.ImageDisplay;
import imagej.data.display.ImageDisplayService;
import imagej.data.table.DefaultResultsTable;
import imagej.data.table.ResultsTable;
import imagej.display.Display;
import imagej.display.DisplayService;
import imagej.menu.MenuConstants;
import imagej.module.ItemIO;
import imagej.plugin.Menu;
import imagej.plugin.Parameter;
import imagej.plugin.Plugin;


@Plugin(menu = {
	@Menu(label = MenuConstants.IMAGE_LABEL, weight = MenuConstants.IMAGE_WEIGHT,
		mnemonic = MenuConstants.IMAGE_MNEMONIC),
	@Menu(label = "Color"),
	@Menu(label = "Show LUT As Table", weight = 13) })
public class ShowLUTAsTable extends ContextCommand {

	@Parameter
	private ImageDisplayService imgDispService;
	
	@Parameter
	private DisplayService displayService;
	
	@Parameter
	private ImageDisplay display;
	
	@Parameter(type = ItemIO.OUTPUT)
	private Display<?> output;
	
	@Override
	public void run() {
		DatasetView view = imgDispService.getActiveDatasetView(display);
		List<ColorTable> colorTables = view.getColorTables();
		Position planePos = view.getPlanePosition();
		long pos = planePos.getIndex();
		if (pos < 0 || pos >= colorTables.size()) pos = 0;
		ColorTable colorTable = colorTables.get((int)pos);
		int rowCount = colorTable.getLength();
		int componentCount = colorTable.getComponentCount();
		int colCount = componentCount + 1;
		ResultsTable table = new DefaultResultsTable(colCount, rowCount);
		for (int y = 0; y < rowCount; y++) {
			table.setValue(y, 0, y);
			for (int x = 0; x < componentCount; x++) {
				double value = colorTable.get(x, y);
				table.setValue(value, x+1, y);
			}
		}
		output = displayService.createDisplay(table);
		output.setName("Color table");
	}
}
