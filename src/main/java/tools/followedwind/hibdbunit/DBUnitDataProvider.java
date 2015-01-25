package tools.followedwind.hibdbunit;

import org.dbunit.dataset.IDataSet;


public interface DBUnitDataProvider {
	public IDataSet getData();
}