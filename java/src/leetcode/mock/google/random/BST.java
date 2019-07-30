package leetcode.mock.google.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BST {
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

    public boolean findTarget(TreeNode root, int k) {
    	// Traverse in-order to get the numbers into a list
    	List<Integer> list = new ArrayList<>();
    	inorder(list, root);
    	for(int i=list.size()-1; i>=0; i--) {
    		int toFind = k-list.get(i);
    		list.remove(i);
    		if(binarySearch(list, 0, i-1, toFind)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean binarySearch(List<Integer> list, int start, int end, int n) {
    	return Collections.binarySearch(list, n)>-1;
    }
    
    private void inorder(List<Integer> list, TreeNode root) {
    	if(root == null) return;
    	inorder(list, root.left);
		list.add(root.val);
		inorder(list, root.right);
    }
	
}
