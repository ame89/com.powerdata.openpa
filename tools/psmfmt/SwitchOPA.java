package com.powerdata.openpa.tools.psmfmt;

import com.powerdata.openpa.BusList;
import com.powerdata.openpa.PAModel;
import com.powerdata.openpa.PAModelException;
import com.powerdata.openpa.SwitchList;
import com.powerdata.openpa.pwrflow.BusRefIndex;

public class SwitchOPA extends ExportOpenPA<SwitchList>
{
	public SwitchOPA(PAModel m, BusRefIndex bri) throws PAModelException
	{
		super(m.getSwitches(), Switch.values().length);
		int[][] bx = bri.get2TBus(_list);
		BusList buses = bri.getBuses();
		assign(Switch.ID, new StringWrap(i -> _list.getID(i)));
		assign(Switch.Name, new StringWrap(i -> _list.getName(i)));
		assign(Switch.Node1, new StringWrap(i -> buses.get(bx[0][i]).getID()));
		assign(Switch.Node2, new StringWrap(i -> buses.get(bx[1][i]).getID()));
	}
	
	@Override
	protected String getPsmFmtName()
	{
		return PsmMdlFmtObject.Switch.toString();
	}
}
