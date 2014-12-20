/**
 * 
 */
package cn.mxj.hibernate;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLInnoDBDialect;

/**
 * @author fl
 * 
 */
public class MySQLInnoDBDialectPlus extends MySQLInnoDBDialect {

	public MySQLInnoDBDialectPlus() {
		super();
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
		registerHibernateType(Types.DECIMAL, Hibernate.BIG_DECIMAL.getName());
	}
}
