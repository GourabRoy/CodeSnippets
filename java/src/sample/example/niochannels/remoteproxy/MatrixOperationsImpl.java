package sample.example.niochannels.remoteproxy;

public class MatrixOperationsImpl implements MatrixOperations {

	public Matrix add(Matrix m1, Matrix m2) {
		Matrix.Size size = m1.getSize();
		Matrix result = new Matrix(size);
		for(int i=0; i<size.getX(); i++) {
			for(int j=0; j<size.getY(); j++) {
				result.set(i, j, m1.get(i, j) + m2.get(i, j));
			}
		}
		return result;
	}

}
