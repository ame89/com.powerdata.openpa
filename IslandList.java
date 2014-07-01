package com.powerdata.openpa;

import com.powerdata.openpa.Gen.Mode;
import com.powerdata.openpa.Switch.State;

public class IslandList extends GroupList<Island>
{
	boolean[] _egzd = new boolean[_size];
	float[][] _freq = IFlt();
	
	public static final IslandList Empty = new IslandList();
	
	IslandList(){};
	
	public IslandList(PALists model)
	{
		super(model, new BusGrpMapBldr(model)
		{
			@Override
			protected boolean incSW(Switch d)
			{
				return d.getState() == State.Closed;
			}

			@Override
			protected boolean incLN(Line d)
			{
				return d.isInSvc();
			}

			@Override
			protected boolean incSR(SeriesReac d)
			{
				return d.isInSvc();
			}

			@Override
			protected boolean incSC(SeriesCap d)
			{
				return d.isInSvc();
			}

			@Override
			protected boolean incTX(Transformer d)
			{
				return d.isInSvc();
			}

			@Override
			protected boolean incPS(PhaseShifter d)
			{
				return d.isInSvc();
			}

			@Override
			protected boolean incD2(TwoTermDCLine d)
			{
				return d.isInSvc();
			}
		}.addAll().getMap());
		
		String[] id = new String[_size];
		for(int i=0; i < _size; ++i)
			id[i] = String.valueOf(i+1);
		setName(id);
		setID(id);
		setupEgStatus();
	}
	
	@Override
	public Island get(int index)
	{
		return new Island(this, index);
	}
	
	void setupEgStatus()
	{
		int n = size();
		_egzd = new boolean[n];
		for(int i=0; i < n; ++i)
		{
			GenList gens = getGenerators(i);
			for(Gen g : gens)
			{
				Mode m = g.getMode();
				if (m != Mode.OFF && m != Mode.PMP)
				{
					_egzd[i] = true;
					break;
				}
			}
		}
	}
	
	public boolean isEnergized(int ndx)
	{
		return _egzd[ndx];
	}

	public boolean[] isEnergized()
	{
		return _egzd.clone();
	}
	
	public float getFreq(int ndx)
	{
		return getFloat(_freq, ndx);
	}

	public void setFreq(int ndx, float f)
	{
		setFloat(_freq, ndx, f);
	}
	
	public float[] getFreq()
	{
		return getFloat(_freq);
	}
	
	public void setFreq(float[] f)
	{
		setFloat(_freq, f);
	}
	
	
	public static void main(String[] args) throws Exception
	{
		PAModel m = PflowModelBuilder.Create(
			"pd2cim:sdb=/run/shm/config.pddb&db=/home/chris/"+
			"Documents/testmodels/public/palco/exports/cim.pddb")
			.load();
		
		for(Island i : m.getIslands())
		{
			System.out.println(i);
		}
	}

}