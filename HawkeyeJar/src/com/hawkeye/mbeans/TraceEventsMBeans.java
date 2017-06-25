package com.hawkeye.mbeans;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;



public class TraceEventsMBeans implements DynamicMBean {

	private String packageName;
	private static TraceEventsMBeans mbean = new TraceEventsMBeans();

	private boolean traceEnabled = false;

	public static final String TYPE_STRING = "java.lang.String";
	public static final String TYPE_VOID = "void";
	public static final String TYPE_BOOLEAN = "boolean";

	private TraceEventsMBeans() {
		String flag = "";
		flag = System.getProperty("traceAllEnabled");
		if (flag != null && flag.equalsIgnoreCase("true")) {
			try {
				this.setAttribute(new Attribute("traceEnabled", new Boolean(
						true)));
			} catch (AttributeNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidAttributeValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MBeanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ReflectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static TraceEventsMBeans getInstance() {
		return mbean;

	}

	public boolean getTraceEnabled() {
		return this.traceEnabled;
	}

	public void TraceEnable() {
		this.traceEnabled = true;
	}

	public void TraceDisable() {
		this.traceEnabled = false;
	}

	public void setTraceEnabled(boolean traceenabled) {
		this.traceEnabled = traceenabled;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void Packaging(String packageName) {

		this.packageName = packageName;
	}

	public String getPackagename() {

		return this.packageName;
	}

	@Override
	public Object getAttribute(String name) throws AttributeNotFoundException,
			MBeanException, ReflectionException {

		if (name.equals("packageName")) {

			return getPackagename();

		} else if (name.equals("traceEnabled")) {

			return getTraceEnabled();

		}

		else

			throw new AttributeNotFoundException("No such attribute: " + name);
	}

	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException,
			MBeanException, ReflectionException {
		String name = attribute.getName();

		Object value = attribute.getValue();

		if (name.equals("packageName"))

			this.Packaging((String) value);

		else if (name.equals("traceEnabled"))

			this.setTraceEnabled((Boolean) value);

		else
			throw new AttributeNotFoundException(name);

	}

	@Override
	public AttributeList getAttributes(String[] names) {
		AttributeList list = new AttributeList();

		for (String name : names) {

			String value = this.getPackagename();

			if (value != null)

				list.add(new Attribute(name, value));

		}

		return list;

	}

	@Override
	public AttributeList setAttributes(AttributeList list) {

		Attribute[] attrs = (Attribute[]) list.toArray(new Attribute[0]);

		AttributeList retlist = new AttributeList();

		for (Attribute attr : attrs) {

			String name = attr.getName();

			Object value = attr.getValue();

			if ((value instanceof String)) {

				this.Packaging(value.toString());

				retlist.add(new Attribute(name, value));

			}
		}
		return retlist;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {

		if (actionName == null) {
			throw new RuntimeOperationsException(new IllegalArgumentException(
					"Operation name cannot be null"),
					"Cannot invoke a null operation in "
							+ this.getClass().getName());
		}

		if (actionName.equals("Packaging")) {
			Packaging((String) params[0]);
			return null;
		} else if (actionName.equals("TraceEnable")) {
			TraceEnable();
			return null;
		} else if (actionName.equals("TraceDisable")) {
			TraceDisable();
			return null;
		}

		else {
			// unrecognized operation name:
			throw new ReflectionException(
					new NoSuchMethodException(actionName),
					"Cannot find the operation " + actionName + " in "
							+ this.getClass().getName());
		}

	}

	@Override
	public MBeanInfo getMBeanInfo() {

		MBeanAttributeInfo[] attrs = { new MBeanAttributeInfo("packageName",
				TYPE_STRING, "For Trace" + "packageName", true, true, false) };
		MBeanParameterInfo[] params = { new MBeanParameterInfo("packageName",
				TYPE_STRING, "package name") };

		MBeanOperationInfo[] opers = { new MBeanOperationInfo("Packaging",

		"Packaging desc", params, TYPE_VOID, 1),
				new MBeanOperationInfo("TraceEnable",

				"Enabling Trace", null, TYPE_VOID, 1),
				new MBeanOperationInfo("TraceDisable",

				"Disabling Trace", null, TYPE_VOID, 1) };

		return new MBeanInfo(getClass().getName(), "For Trace",

		attrs, null, opers, null);
	}

}
