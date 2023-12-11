package septagram.Theomachy.Utility;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>�����ڵ�(Unicode) ������� ���� �ѱ� ���� Ŭ�����Դϴ�.</p>
 * 
 * 
 * <h2>�����ڵ�</h2>
 * <p>�����ڵ�(<a href="http://www.unicode.org/">Unicode</a>)�� �������� ��� ���ڸ� ��ǻ�Ϳ��� �ϰ��ǰ� ǥ���ϰ� �ٷ� �� �ֵ��� ������ ǥ���Դϴ�.
 * </p>
 * 
 * <h2>Unicode Korean specific</h2>
 * <ul>
 * 	<li>0x1100-0x11F9 : <a href="http://www.unicode.org/charts/PDF/U1100.pdf">�ѱ� �ڸ�(Hangul Jamo) ����</a> - ������ ����
 * 	<li>0x3130-0x318E : <a href="http://www.unicode.org/charts/PDF/U3130.pdf">�ѱ� ȣȯ �ڸ�(Hangul Compatibility Jamo) ����</a> 
 * 	<li>0xAC00-0xD7A3 : <a href="http://www.unicode.org/charts/PDF/UAC00.pdf">�ѱ� (Hangul Syllables) ����</a> - �ϼ��� ���� 
 * 	<li>0xFF00-0xFFEF : <a href="http://www.unicode.org/charts/PDF/UFF00.pdf">Halfwidth Jamo</a>
 * </ul>
 * 
 * <h2>�����ڵ忡���� �ѱ�</h2>
 * <p>�����ڵ� CharacterSet���� �ѱ��� �����ϴ� ������ �� ���� ���� �� �ֽ��ϴ�. 
 * ù��°�� 0x1100-0x11F9 ������ �ѱ� �ڸ� ����. �ѱۿ��� ���Ǵ� �ڸ���� �ʼ� ����/�߼� ����/���� �������� ���� ������ �ѱ��ھ� ������ŵ�ϴ�.
 * �� ������ �ִ� ���ڵ��� �̿��ϸ� �ѱ� ������ ǥ���� ���������ϴ�. ĳ���ͼ� ��ȯ�� ��ƴٴ� ������ �ֽ��ϴ�. 
 * �ι�°�� 0x3130-0x318E ������ �ѱ� ȣȯ �ڸ� ����. �� �κ��� ��/��/������ �������� �ʰ� �׳� ���Ǵ� ��� �ڸ���� �ѵ� ���� ��� �ֽ��ϴ�. 
 * ����°�� 0xAC00-0xD7A3 ������ �ѱ� ����. �� �κп��� ���� �ѱ� �ڸ�� ǥ�� ������ ��� �ѱ� ���ڵ�(11172��=19*21*28))�� ��� �ֽ��ϴ�. 
 * ������ ����� ���� �ڸ� �����ϴ� ���� �����մϴ�. 
 * �� �� ���ڵ��� �ٸ� ������ ���ԵǾ� �ֽ��ϴ�.</p>
 * 
 * <h2>�ڸ� ����</h2>
 * <ul>
 * 	<li>0x3130-0x314E : ��������(40��)
 * 	<li>0x314F-0x3163 : �������(21��)
 * 	<li>0x3164        : ä���ڵ�
 * 	<li>0x3165-0x318E : �����ڸ�
 * </ul>
 * 
 * <h2>��/��/���� �����ϱ�</h2>
 * <ul>
 * 	<li>�ѱ� : 0xAC00 + (�ʼ�_I * 21 * 28) + (�߼�_I * 28) + ����_I
 * 	<li>�ʼ�_I : (�ѱ� - 0xAC00) / (21 * 28)
 * 	<li>�߼�_I : ((�ѱ� - 0xAC00) % (21 * 28)) / 28
 * 	<li>����_I : ((�ѱ� - 0xAC00) % (21 * 28)) % 28
 * </ul>
 * 
 * <h2>���� �ڷ�</h2>
 * <ul>
 * 	<li><a href="http://www.kristalinfo.com/K-Lab/unicode/Unicode_intro-kr.html">�����ڵ�(Unicode)�� �����ڵ� ���ڵ�</a>
 * </ul>
 *   
 *  
 * @author <a href="mailto:kangwoo@jarusoft.com">kangwoo</a>
 * @version 1.0
 * @since 1.0
 */
public class Hangul {

	private static final char HANGUL_SYLLABLES_BEGIN = 0xAC00;
	private static final char HANGUL_SYLLABLES_END = 0xD7A3;
	
	private static final char HANGUL_COMPATIBILITY_JAMO_BEGIN = 0x3130;
	private static final char HANGUL_COMPATIBILITY_JAMO_END = 0x318E;
	
	private static final char HANGUL_COMPATIBILITY_JA_BEGIN = 0x3130;
	private static final char HANGUL_COMPATIBILITY_JA_END = 0x314E;
	
	private static final char HANGUL_COMPATIBILITY_MO_BEGIN = 0x314F;
	private static final char HANGUL_COMPATIBILITY_MO_END = 0x3163;
	
	private static final char HANGUL_JAMO_BEGIN = 0x1100;
	private static final char HANGUL_JAMO_END = 0x11F9;

	/**
	 * '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��'
	 */
	private static final char[] HANGUL_CHOSEONG = {0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};
	/**
	 * '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��'
	 */
	private static final char[] HANGUL_JUNGSEONG = {0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163};
	/**
	 * ' ', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��'
	 */
	private static final char[] HANGUL_JONGSEONG = {0x0000, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};

	public static final int HANGUL_CHOSEONG_SIZE = HANGUL_CHOSEONG.length;	// 19
	public static final int HANGUL_JUNGSEONG_SIZE = HANGUL_JUNGSEONG.length;	// 21
	public static final int HANGUL_JONGSEONG_SIZE = HANGUL_JONGSEONG.length;	// 28
	
	private static final Map<Character, Integer> HANGUL_CHOSEONG_CODE_TABLE = new HashMap<Character, Integer>();
	private static final Map<Character, Integer> HANGUL_JUNGSEONG_CODE_TABLE = new HashMap<Character, Integer>();
	private static final Map<Character, Integer> HANGUL_JONGSEONG_CODE_TABLE = new HashMap<Character, Integer>();
	
	static {
		for (int i = 0; i < HANGUL_CHOSEONG.length; i++) {
			HANGUL_CHOSEONG_CODE_TABLE.put(HANGUL_CHOSEONG[i], i);
		}
		for (int i = 0; i < HANGUL_JUNGSEONG.length; i++) {
			HANGUL_JUNGSEONG_CODE_TABLE.put(HANGUL_JUNGSEONG[i], i);
		}
		for (int i = 0; i < HANGUL_JONGSEONG.length; i++) {
			HANGUL_JONGSEONG_CODE_TABLE.put(HANGUL_JONGSEONG[i], i);
		}
	}
	
	/**
	 * <p>�Է��� ���ڰ� �ѱ����� �Ǵ��Ѵ�.</p>
	 * <p>(�ڵ尪�� �ѱ� ����(0xAC00-0xD7A3)���� �Ǵ�)</p>
	 * 
	 * @param c
	 * @return �ѱ��̸� <code>true</code>, �ƴϸ� <code>false</code>
	 */
	public static boolean isHangulSyllables(char ch) {
		return ch >= HANGUL_SYLLABLES_BEGIN && ch <= HANGUL_SYLLABLES_END;
	}
	
	/**
	 * <p>�Է��� ���ڰ� �ѱ� ȣȯ �ڸ����� �Ǵ��Ѵ�.</p>
	 * <p>(�ڵ尪�� �ѱ� ȣȯ �ڸ� ����(0x1100-0x318E)���� �Ǵ�)</p>
	 * 
	 * @param ch
	 * @return �ѱ� ȣȯ �ڸ��̸� <code>true</code>, �ƴϸ� <code>false</code>
	 */
	public static boolean isHangulCompatibilityJamo(char ch) {
		return ch >= HANGUL_COMPATIBILITY_JAMO_BEGIN && ch <= HANGUL_COMPATIBILITY_JAMO_END;
	}

	/**
	 * <p>�Է��� ���ڰ� �ѱ� ȣȯ ���� �� ���� �������� �Ǵ��Ѵ�.</p>
	 * <p>(�ڵ尪�� �ѱ� ȣȯ �ڸ� ������ ���� ����(0x3130-0x314E)���� �Ǵ�)</p>
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isHangulCompatibilityJa(char ch) {
		return ch >= HANGUL_COMPATIBILITY_JA_BEGIN && ch <= HANGUL_COMPATIBILITY_JA_END;
	}
	
	/**
	 * <p>�Է��� ���ڰ� �ѱ� ȣȯ ������ ���� �������� �Ǵ��Ѵ�.</p>
	 * <p>(�ڵ尪�� �ѱ� ȣȯ �ڸ� ������ ���� ����(0x314F-0x3163)���� �Ǵ�)</p>
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isHangulCompatibilityMo(char ch) {
		return ch >= HANGUL_COMPATIBILITY_MO_BEGIN && ch <= HANGUL_COMPATIBILITY_MO_END;
	}

	/**
	 * <p>�Է��� ���ڰ� �ѱ� �ڸ����� �Ǵ��Ѵ�.</p>
	 * <p>(�ڵ尪�� �ѱ� �ڸ� ����(0x3130-0x11F9)���� �Ǵ�)</p>
	 * 
	 * @param ch
	 * @return �ѱ� �ڸ��̸� <code>true</code>, �ƴϸ� <code>false</code>
	 */
	public static boolean isHangulJamo(char ch) {
		return ch >= HANGUL_JAMO_BEGIN && ch <= HANGUL_JAMO_END;
	}
	
	/**
	 * <p>�ʼ��� �����Ѵ�.</p>
	 * 
	 * @param ch
	 * @return
	 */
	public static char getChoseong(char ch) {
		if (isHangulSyllables(ch) == false) {
			throw new IllegalArgumentException("�Է°��� �߸��Ǿ����ϴ�. (" + ch + ")");
		}
		int hCode = (ch - HANGUL_SYLLABLES_BEGIN) / (HANGUL_JUNGSEONG_SIZE * HANGUL_JONGSEONG_SIZE);
		return HANGUL_CHOSEONG[hCode];
	}
	
	/**
	 * <p>�߼��� �����Ѵ�.</p>
	 * 
	 * @param ch
	 * @return
	 */
	public static char getJungseong(char ch) {
		if (isHangulSyllables(ch) == false) {
			throw new IllegalArgumentException("�Է°��� �߸��Ǿ����ϴ�. (" + ch + ")");
		}
		int hCode = ((ch - HANGUL_SYLLABLES_BEGIN) % (HANGUL_JUNGSEONG_SIZE * HANGUL_JONGSEONG_SIZE)) / HANGUL_JONGSEONG_SIZE;
		return HANGUL_JUNGSEONG[hCode];
	}
	
	/**
	 * <p>������ �����Ѵ�.</p>
	 * 
	 * @param ch
	 * @return
	 */
	public static char getJongseong(char ch) {
		if (isHangulSyllables(ch) == false) {
			throw new IllegalArgumentException("�Է°��� �߸��Ǿ����ϴ�. (" + ch + ")");
		}
		int hCode = ((ch - HANGUL_SYLLABLES_BEGIN) % (HANGUL_JUNGSEONG_SIZE * HANGUL_JONGSEONG_SIZE)) % HANGUL_JONGSEONG_SIZE;
		return HANGUL_JONGSEONG[hCode];
	}
	
	/**
	 * <p>��/��/������ �����Ѵ�.
	 * </p>
	 * @param ch
	 * @return [�ʼ�, �߼�, ����] or [�ʼ�, �߼�]
	 */
	public static char[] getJamo(char ch) {
		char[] jamo = new char[3];
		jamo[0] = getChoseong(ch);
		jamo[1] = getJungseong(ch);
		jamo[2] = getJongseong(ch);
		if (jamo[2] == HANGUL_JONGSEONG[0]) {
			char[] temp = new char[2];
			temp[0] = jamo[0];
			temp[1] = jamo[1];
			jamo = temp;
		}
		return jamo;
	}
	
	/**
	 * <p>������ �����ϴ��� ���θ� �Ǵ��Ѵ�.</p>
	 * 
	 * @param ch
	 * @return ������ �����ϸ� <code>true</code>, �ƴϸ� <code>false</code>
	 */
	public static boolean hasJongseong(char ch) {
		return getJongseong(ch) != HANGUL_JONGSEONG[0];
	}
	
	/**
	 * <p>�ʼ�, �߼��� �����Ͽ� �ѱ��ڷ� �����.</p>
	 * 
	 * @param choseong �ʼ�
	 * @param jungseong �߼�
	 * @return
	 */
	public static char toHangul(char choseong, char jungseong) {
		return toHangul(choseong, jungseong, HANGUL_JONGSEONG[0]);
	}
	
	/**
	 * <p>�ʼ�, �߼�, ������ �����Ͽ� �ѱ��ڷ� �����.</p>
	 * 
	 * @param choseong �ʼ�
	 * @param jungseong �߼�
	 * @param jongseong ����
	 * @return
	 */
	public static char toHangul(char choseong, char jungseong, char jongseong) {
		Integer choseongIndex = HANGUL_CHOSEONG_CODE_TABLE.get(choseong);
		if (choseongIndex == null) {
			throw new IllegalArgumentException("�ʼ��� �߸��Ǿ����ϴ�. (" + choseong + ")");
		}
		Integer jungseongIndex = HANGUL_JUNGSEONG_CODE_TABLE.get(jungseong);
		if (jungseongIndex == null) {
			throw new IllegalArgumentException("�߼��� �߸��Ǿ����ϴ�. (" + jungseong + ")");
		}
		if (jongseong == ' ') {
			jongseong = HANGUL_JONGSEONG[0];
		}
		Integer jongseongIndex = HANGUL_JONGSEONG_CODE_TABLE.get(jongseong);
		if (jongseongIndex == null) {
			throw new IllegalArgumentException("������ �߸��Ǿ����ϴ�. (" + jongseong + ")");
		}
		return (char)(HANGUL_SYLLABLES_BEGIN + (choseongIndex * (HANGUL_JUNGSEONG_SIZE * HANGUL_JONGSEONG_SIZE)) + (jungseongIndex * HANGUL_JONGSEONG_SIZE) + jongseongIndex);
	}
	
	/**
	 * <p>���ڿ� �˸´� ���縦 �����Ѵ�.</p>
	 * 
	 * <pre>
	 * Hangul.getJosa('��', '��', '��') = '��'
	 * Hangul.getJosa('��', '��', '��') = '��'
	 * </pre>
	 * 
	 * @param ch
	 * @param aux1 ������ ������ ���� ����
	 * @param aux2 ������ ������ ���� ����
	 * @return
	 */
	public static char getJosa(char ch, char aux1, char aux2) {
		return hasJongseong(ch) ? aux1 : aux2;
	}

	/**
	 * <p>���ڿ� �˸´� ���縦 �����Ѵ�.</p>
	 * 
	 * <pre>
	 * Hangul.getJosa('��', "��", "��") = "��"
	 * Hangul.getJosa('��', "��", "��") = "��"
	 * </pre>
	 * 
	 * @param ch
	 * @param aux1 ������ ������ ���� ����
	 * @param aux2 ������ ������ ���� ����
	 * @return
	 */
	public static String getJosa(char ch, String aux1, String aux2) {
		return hasJongseong(ch) ? aux1 : aux2;
	}
}
