package sy.hbm;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import sy.dao.impl.BaseDaoImpl;

import com.chamago.pcrm.common.utils.Utils;
public class Test {

	public static void main(String[] ag){
		
		ApplicationContext ac=Utils.getClassPathXMlApplication();
		BaseDaoImpl sf=(BaseDaoImpl)ac.getBean(BaseDaoImpl.class);
		 
		try {
			System.out.println(sf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
