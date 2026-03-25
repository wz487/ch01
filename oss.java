package oss_1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class oss {
	 private User currentUser;
	    private boolean isAdmin;
	    private List<Product> products;
	    private JFrame mainFrame;
	    private JTextField nameField;
	    private JTextField phoneField;
	    private static UserManager userManager = new UserManager();

	    public oss(User user, boolean admin) {
	        this.currentUser = user;
	        this.isAdmin = admin;
	        initData();
	        createMainUI();
	    }
	    

    private void initData() {
    	
    }
    private void createMainUI() {
    	mainFrame = new JFrame();
        mainFrame.setTitle(isAdmin ? "관리자 모드" : "고객 메인");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel imageLabel = new JLabel(new ImageIcon());
        imageLabel.setText("(图片占位)");
        topPanel.add(imageLabel);
        mainFrame.add(topPanel, BorderLayout.NORTH);

      
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        centerPanel.add(new JLabel("이 름:"));
        nameField = new JTextField(15);
        if (!isAdmin && currentUser != null && currentUser.getCustomerInfo() != null) {
            nameField.setText(currentUser.getCustomerInfo().getName());
        }
        centerPanel.add(nameField);

        centerPanel.add(new JLabel("연락처:"));
        phoneField = new JTextField(15);
        if (!isAdmin && currentUser != null && currentUser.getCustomerInfo() != null) {
            phoneField.setText(currentUser.getCustomerInfo().getPhone());
        }
        centerPanel.add(phoneField);

        JButton shoppingBtn = new JButton("쇼핑하기");
        centerPanel.add(new JLabel()); 
        centerPanel.add(shoppingBtn);

        mainFrame.add(centerPanel, BorderLayout.CENTER);

        shoppingBtn.addActionListener(e -> openShoppingWindow());

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    private void openShoppingWindow() {
    	
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
