package com.magic.promotion.util;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class EnumTypeHandlerString extends BaseTypeHandler<Enum> implements TypeHandler<Enum> {
	
	private Class<Enum> clazz;

	public EnumTypeHandlerString(Class<Enum> clazz) {
		this.clazz = clazz;
	}

	public void setNonNullParameter(PreparedStatement ps, int i,
			Enum parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.toString());
	}

	public Enum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		final String val = rs.getString(columnName);
		Enum obj = null;
        if (!rs.wasNull() ) { 
            @SuppressWarnings("unchecked")
            EnumSet enumSet = EnumSet.allOf(clazz);
            for (Object object : enumSet) {
                if (object instanceof Enum) {
                    Enum e = (Enum) object;
                    if ((e.ordinal()+"").equals(val)) {
                        obj = e;
                    }
                }
            }
        }
        return obj;
	}
	
	
	public Enum getNullableResult(ResultSet rs, int index)
			throws SQLException {
		final String val = rs.getString(index);
		Enum obj = null;
        if (!rs.wasNull() ) {
            @SuppressWarnings("unchecked")
            EnumSet enumSet = EnumSet.allOf(clazz);
            for (Object object : enumSet) {
                if (object instanceof Enum) {
                    Enum e = (Enum) object;
                    if ((e.ordinal()+"").equals(val)) {
                        obj = e;
                    }
                }
            }
        }
        return obj;
	}

	public Enum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		final String val = cs.getString(columnIndex);
		Enum obj = null;
        if (!cs.wasNull() ) {
            @SuppressWarnings("unchecked")
            EnumSet enumSet = EnumSet.allOf(clazz);
            for (Object object : enumSet) {
                if (object instanceof Enum) {
                    Enum e = (Enum) object;
                    if ((e.ordinal()+"").equals(val)) {
                        obj = e;
                    }
                }
            }
        }
        return obj;
	}

	public void setParameter(PreparedStatement ps, int i, Enum parameter,
			JdbcType jdbcType) throws SQLException {
		if(parameter==null)
			ps.setObject(i,null);
		else{
			if(parameter instanceof Enum){
				ps.setString(i,parameter.ordinal()+"");
			}
		}
		
	}

	public Enum getResult(ResultSet rs, String columnName) throws SQLException {

		final String val = rs.getString(columnName);
		Enum obj = null;
        if (!rs.wasNull() ) {
            @SuppressWarnings("unchecked")
            EnumSet enumSet = EnumSet.allOf(clazz);
            for (Object object : enumSet) {
                if (object instanceof Enum) {
                    Enum e = (Enum) object;
                    if ((e.ordinal()+"").equals(val)) {
                        obj = e;
                    }
                }
            }
        }
        return obj;
	
	}

	public Enum getResult(ResultSet rs, int columnIndex) throws SQLException {

		final String val = rs.getString(columnIndex);
		Enum obj = null;
        if (!rs.wasNull() ) {
            @SuppressWarnings("unchecked")
            EnumSet enumSet = EnumSet.allOf(clazz);
            for (Object object : enumSet) {
                if (object instanceof Enum) {
                	
                    Enum e = (Enum) object;
                    if ((e.ordinal()+"").equals(val)) {
                        obj = e;
                    }
                }
            }
        }
        return obj;
	
	}

	public Enum getResult(CallableStatement cs, int columnIndex)
			throws SQLException {

		final String val = cs.getString(columnIndex);
		Enum obj = null;
        if (!cs.wasNull() ) {
            @SuppressWarnings("unchecked")
            EnumSet enumSet = EnumSet.allOf(clazz);
            for (Object object : enumSet) {
                if (object instanceof Enum) {
                    Enum e = (Enum) object;
                    if ((e.ordinal()+"").equals(val)) {
                        obj = e;
                    }
                }
            }
        }
        return obj;
	
	}
}
