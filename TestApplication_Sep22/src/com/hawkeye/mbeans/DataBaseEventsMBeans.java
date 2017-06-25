package com.hawkeye.mbeans;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;

public class DataBaseEventsMBeans implements DynamicMBean {
	private boolean dataBaseEventEnabled = false;

	public static final String TYPE_VOID = "void";
	public static final String TYPE_BOOLEAN = "boolean";
	private static DataBaseEventsMBeans mbean = new DataBaseEventsMBeans();

	private DataBaseEventsMBeans() {
		String flag = "";
		//flag = System.getProperty("dataBaseEventEnabled");
		if (flag != null && flag.equalsIgnoreCase("true")) {
			try {
				this.setAttribute(new Attribute("dataBaseEventEnabled",
						new Boolean(true)));
			} catch (AttributeNotFoundException e) {

				e.printStackTrace();
			} catch (InvalidAttributeValueException e) {

				e.printStackTrace();
			} catch (MBeanException e) {

				e.printStackTrace();
			} catch (ReflectionException e) {

				e.printStackTrace();
			}
		}
	}

	public static DataBaseEventsMBeans getInstance() {
		return mbean;
	}

	public boolean getDatabaseEventEnabled() {
		return this.dataBaseEventEnabled;
	}

	public void databaseEventEnable() {
		this.dataBaseEventEnabled = true;
	}

	public void databaseEventDisable() {
		this.dataBaseEventEnabled = false;
	}

	public void setDatabaseEventEnabled(boolean dbeventenabled) {
		this.dataBaseEventEnabled = dbeventenabled;
	}

	@Override
	public Object getAttribute(String name) throws AttributeNotFoundException,
			MBeanException, ReflectionException {
		if (name.equals("dataBaseEventEnabled")) {

			return getDatabaseEventEnabled();

		} else
			throw new AttributeNotFoundException("No such attribute: " + name);
	}

	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException,
			MBeanException, ReflectionException {

		String name = attribute.getName();

		Object value = attribute.getValue();
		if (name.equals("dataBaseEventEnabled"))

			this.setDatabaseEventEnabled((Boolean) value);
		else
			throw new AttributeNotFoundException(name);

	}

	@Override
	public AttributeList getAttributes(String[] names) {
		return null;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		if (actionName.equals("databaseEventEnable")) {
			databaseEventEnable();
			return null;
		} else if (actionName.equals("databaseEventDisable")) {
			databaseEventDisable();
			return null;
		} else {
			// unrecognized operation name:
			throw new ReflectionException(
					new NoSuchMethodException(actionName),
					"Cannot find the operation " + actionName + " in "
							+ this.getClass().getName());
		}
	}

	@Override
	public MBeanInfo getMBeanInfo() {

		MBeanOperationInfo[] opers = {
				new MBeanOperationInfo("databaseEventEnable",

				"Enabling Database event", null, TYPE_VOID, 1),
				new MBeanOperationInfo("databaseEventDisable",

				"Disabling Database event", null, TYPE_VOID, 1) };
		return new MBeanInfo(getClass().getName(),
				"For DataBaseEvent Diagnostics",

				null, null, opers, null);

	}
}
