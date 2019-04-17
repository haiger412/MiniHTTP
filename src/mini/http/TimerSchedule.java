package mini.http;
/**
 * 定时任务接口。
 * @author Administrator
 *
 */
public interface TimerSchedule {
	/**
	 * 当定时时间到来时。执行的事情。
	 */
	public void executeSchedule();
	/**
	 * 定时时间。
	 * @return
	 */
	public int getMinutes();
}
