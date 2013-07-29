package com.powerdata.openpa.psse;

import com.powerdata.openpa.tools.Complex;
import com.powerdata.openpa.tools.PAMath;
import com.powerdata.openpa.tools.PComplex;

public abstract class BusList extends PsseBaseList<Bus>
{
	public static final BusList Empty = new BusList()
	{
		@Override
		public int getI(int ndx) throws PsseModelException {return 0;}
		@Override
		public String getObjectID(int ndx) throws PsseModelException {return null;}
		@Override
		public int size() {return 0;}
	};
	
	protected BusList() {super();}
	public BusList(PsseModel model) {super(model);}

	/** Get a Bus by it's index. */
	@Override
	public Bus get(int ndx) { return new Bus(ndx,this); }
	/** Get a Bus by it's ID. */
	@Override
	public Bus get(String id) { return super.get(id); }
	
	/* groups */
	public GenList getGenerators(int ndx) throws PsseModelException {return GenList.Empty;}
	public LoadList getLoads(int ndx) throws PsseModelException {return LoadList.Empty;}
	public LineList getLines(int ndx) throws PsseModelException {return LineList.Empty;}
	public TransformerList getTransformers(int ndx) throws PsseModelException {return TransformerList.Empty;}
	public ShuntList getShunts(int ndx) throws PsseModelException {return ShuntList.Empty;}
	public SvcList getSvcs(int ndx) throws PsseModelException {return SvcList.Empty;}
	public PhaseShifterList getPhaseShifters(int ndx) throws PsseModelException {return PhaseShifterList.Empty;}
	public SwitchList getSwitches(int ndx) throws PsseModelException {return SwitchList.Empty;}

	/* convenience methods */
	/** request that this node be isloated */
	public SwitchList isolate(int ndx) throws PsseModelException { return SwitchList.Empty; }
	/** test if the bus is energized */
	public boolean isEnergized(int ndx) throws PsseModelException {return false;}
	/** get the island number */
	public int getIsland(int ndx) throws PsseModelException {return 0;}

	/** enumerated IDE code */
	public BusTypeCode getBusType(int ndx) throws PsseModelException {return BusTypeCode.fromCode(getIDE(ndx));}
	/** Area */
	public Area getAreaObject(int ndx) throws PsseModelException
	{
		return _model.getAreas().get(getAREA(ndx));
	}
	/** Zone */
	public Zone getZoneObject(int ndx) throws PsseModelException {return _model.getZones().get(getZONE(ndx));}
	/** Owner */
	public Owner getOwnerObject(int ndx) throws PsseModelException {return _model.getOwners().get(getOWNER(ndx));}
	/** get complex shunt admittance to ground */
	public Complex getShuntY(int ndx) throws PsseModelException
	{
		return new Complex(PAMath.mw2pu(getGL(ndx)), PAMath.mvar2pu(getBL(ndx)));
	}
	/** complex bus voltage */
	public PComplex getVoltage(int ndx) throws PsseModelException
	{
		return new PComplex(getVM(ndx), PAMath.deg2rad(getVA(ndx)));
	}
	
	/* raw methods */

	/** Bus number */
	public abstract int getI(int ndx) throws PsseModelException;
	/** Alphanumeric identifier */
	public String getNAME(int ndx) throws PsseModelException {return "";}
	/** Bus base voltage */
	public float getBASKV(int ndx) throws PsseModelException {return 0F;}
	/** Bus type code */
	public int getIDE(int ndx) throws PsseModelException {return 1;}
	/** Active component of shunt admittance to ground in MW at unity voltage*/
	public float getGL(int ndx) throws PsseModelException {return 0F;}
	/** Reactive component of shunt admittance to ground in MVAr at unity voltage*/
	public float getBL(int ndx) throws PsseModelException {return 0F;}
	/** Area number */
	public int getAREA(int ndx) throws PsseModelException {return 1;}
	/** Zone number */
	public int getZONE(int ndx) throws PsseModelException {return 1;}
	/** Bus voltage magnitude p.u.*/
	public float getVM(int ndx) throws PsseModelException {return 1F;}
	/** Bus voltage phase angle in degrees */
	public float getVA(int ndx) throws PsseModelException {return 0F;}
	/** Owner number */
	public int getOWNER(int ndx) throws PsseModelException {return 1;}


	/* realtime methods */

	public void setRTMismatch(int ndx, Complex mismatch)throws PsseModelException {/* do nothing */}
	public Complex getRTMismatch(int ndx)throws PsseModelException {return Complex.Zero;}
}
