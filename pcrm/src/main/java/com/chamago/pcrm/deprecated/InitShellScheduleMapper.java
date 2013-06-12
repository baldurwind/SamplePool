package com.chamago.pcrm.deprecated;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.UpdateProvider;


public interface InitShellScheduleMapper {
	
	/*@Select("SELECT shell_id FROM init_shell_schedule WHERE status='init' AND app_key=#{appkey} AND sellernick=#{sellernick}")
	public List<Integer> findScriptByKey(@Param("appkey") String appkey,@Param("sellernick")  String sellernick);
	
	@Update("UPDATE init_shell_schedule SET status=#{status} WHERE app_key=#{appkey} AND sellernick=#{sellernick} AND shell_id=#{shellId}")
	public Integer updateStatusByKey(@Param("appkey")String appkey,@Param("sellernick")String sellernick,@Param("shellId")Integer shellId,@Param("status")String status);

	
	@Update("UPDATE init_shell_schedule SET status='running' WHERE status='init' AND app_key=#{appkey} AND sellernick=#{sellernick}")
	public int LockInitShellSchedule(@Param("sellernick") String appkey,@Param("sellernick") String sellernick);
		*/	
}
