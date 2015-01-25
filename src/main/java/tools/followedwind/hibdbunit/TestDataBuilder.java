package tools.followedwind.hibdbunit;

import java.io.File;
import java.io.IOException;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

/**
 * 
 * @author followedwind
 *
 */
public class TestDataBuilder {
	
	public TestDataBuilder(){	
	}

	/**
	 * テストデータが定義されている1つ以上のファイルからテストデータオブジェクト作成
	 * @param datafiles テストデータが定義されている1つ以上のファイル
	 * @return 複数ファイルから読み込んだテストデータ
	 * @throws DataSetException データ読み込み中に異常(フォーマット違いなど)が発生
	 * @throws IOException テストデータのファイル読み込みに失敗
	 * @throws IllegalArgumentException 拡張子の取得失敗、拡張子が示すデータの読み込みが実装されていない
	 */
	public IDataSet build( String[] datafiles ) throws DataSetException, IOException {
		IDataSet[] datasets = new IDataSet[datafiles.length];
		int i=0;
		for( String datafile : datafiles ){
			datasets[i++] = this.buildFromFile(datafile);
		}
		return new CompositeDataSet(datasets);
	}

	/**
	 * テストデータファイルの拡張子から形式を判断し読み込む
	 * @param filename テストデータのファイル名
	 * @return 読み込んだテストデータ
	 * @throws DataSetException データ読み込み中に異常(フォーマット違いなど)が発生
	 * @throws IOException テストデータのファイル読み込みに失敗
	 * @throws IllegalArgumentException 拡張子の取得失敗、拡張子が示すデータの読み込みが実装されていない
	 */
	protected IDataSet buildFromFile( String filename ) throws DataSetException, IOException {
		String suffix = getSuffix(filename);
		File file = new File(filename);
		IDataSet dataset = null;
		if ( suffix != null ){
			if ( suffix.equals("xml") ){
				dataset = new FlatXmlDataSetBuilder().build(file);
			} else if ( suffix.equals("csv") ){
				dataset = new CsvDataSet(file);
//			} else if ( suffix.equals("xls") ){
//				dataset = new XlsDataSet(file);
			} else {
				throw new IllegalArgumentException("not support suffix: "+suffix);
			}
		} else {
			throw new IllegalArgumentException("can't get suffix: "+filename);
		}
		return dataset;
	}

	/**
	 * 拡張子取得
	 * @param filename ファイル名
	 * @return 拡張子
	 */
	private static String getSuffix( String filename ){
		int dotpos = filename.lastIndexOf(".");
		if ( dotpos < 0 ){
			return null;
		} else {
			return filename.substring(dotpos+1);
		}
	}
}