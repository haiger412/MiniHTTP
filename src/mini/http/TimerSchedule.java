package mini.http;
/**
 * ��ʱ����ӿڡ�
 * @author Administrator
 *
 */
public interface TimerSchedule {
	/**
	 * ����ʱʱ�䵽��ʱ��ִ�е����顣
	 */
	public void executeSchedule();
	/**
	 * ��ʱʱ�䡣
	 * @return
	 */
	public int getMinutes();
}
