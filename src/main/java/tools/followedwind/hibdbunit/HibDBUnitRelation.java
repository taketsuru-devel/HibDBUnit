package tools.followedwind.hibdbunit;

/**
 * HibernateとDBUnitとの設定を取り持つ<br>
 * 実装例はHibDBUnitRerationDefaultを参照
 * @author followedwind
 * @version 1.0
 */
public interface HibDBUnitRelation {
	/**
	 * このオブジェクトが取り持つデバッグなどの表示用パラメータ名を返す
	 * @return パラメータの表示用の名前
	 */
	public String getType();
	
	/**
	 * このオブジェクトが取り持つDBUnit側のパラメータ名を返す
	 * @return パラメータのDBUnit側の名前
	 */
	public String getDBUnit_key();
	
	/**
	 * このオブジェクトが取り持つHibernate側のパラメータ名を返す
	 * @return パラメータのHibernate側の名前
	 */
	public String getHibernate_key();
}