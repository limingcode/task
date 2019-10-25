
package com.util;
import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;
/**
 *<pre>类说明</pre>
 *<b>功能描述：</b>
 * 注册nvarchar类型映射
 * @author  jinmingming
 * @version 1.0, 2013-4-25
 */
public class SkySQLServerDialectUtil extends SQLServerDialect{

	public SkySQLServerDialectUtil() {
		super();
		registerHibernateType(Types.NVARCHAR, "string");
		this.registerHibernateType(Types.LONGVARCHAR, "string");
	}
}
