package sample.example.niochannels.remoteproxy;

import java.io.Serializable;

public class Matrix implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Size implements Serializable {
		private static final long serialVersionUID = 1L;
		private int x, y;
		
		public Size(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
	}
	
	private Size size;
	private int[][] matrix;
	
	public Matrix(Size size) {
		this(size.x, size.y);
	}
	
	public Matrix(int x, int y) {
		size = new Size(x,y);
		matrix = new int[x][y];
	}

	public Matrix(int[][] m) {
		size.x = m.length;
		size.y = m[0].length;
		matrix = new int[size.x][size.y];
		copyInto(m, matrix);
	}
	
	public Size getSize() {
		return size;
	}
	
	public Integer get(int x, int y) {
		return matrix[x][y];
	}
	
	public void set(int x, int y, int value) {
		matrix[x][y] = value;
	}
	
	static private void copyInto(int[][] source, int[][] target) {
		for(int i=0; i<source.length; i++) {
			for(int j=0; j<source[i].length; j++) {
				target[i][j] = source[i][j];
			}
		}
	}

	static public void copyInto(Matrix source, Matrix target) {
		target.size = source.size;
		copyInto(source.matrix, target.matrix);
	}
	
	public String toString() {
		StringBuilder br = new StringBuilder();
		for(int i=0; i<size.getX(); i++) {
			for(int j=0; j<size.getY(); j++) {
				br.append(matrix[i][j]).append("\t");
			}
			br.append("\n");
		}
		return br.toString();
	}
}
