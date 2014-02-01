
public class TestHarness {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		KeyedRotatedMessage krm = new KeyedRotatedMessage("Hope College");
		System.out.println(krm.getMessage());
		krm.setKey("Key");
		krm.setRotation(3);
		krm.encrypt();
		System.out.println(krm.getMessage());
		RotatedMessage rm = new RotatedMessage("Hope College");
		rm.setRotation(3);
		rm.encrypt();
		System.out.println(rm.getMessage());
		new MP3GUI().setVisible(true);

	}

}
