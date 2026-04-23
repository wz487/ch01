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
    	 products = new ArrayList<>();
         products.add(new Product(1, "공책", 5000));
         products.add(new Product(2, "책가방", 1000));
         products.add(new Product(3, "계산기", 30000));
         products.add(new Product(4, "만년필", 8000));
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
    	JFrame f2 = new JFrame();
        f2.setTitle("장바구니 처리");
        f2.setSize(900, 700);
        f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f2.setLayout(new BorderLayout());

        // 菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu customerMenu = new JMenu("고객");
        JMenu productMenu = new JMenu("상품");
        JMenu cartMenu = new JMenu("장바구니");
        JMenu orderMenu = new JMenu("주문");
        menuBar.add(customerMenu);
        menuBar.add(productMenu);
        menuBar.add(cartMenu);
        menuBar.add(orderMenu);
        f2.setJMenuBar(menuBar);

        JMenuItem showCustomerItem = new JMenuItem("고객 정보 보기");
        customerMenu.add(showCustomerItem);
        showCustomerItem.addActionListener(e -> showCustomerInfo());

        JMenuItem showProductItem = new JMenuItem("상품 목록 보기");
        productMenu.add(showProductItem);
        showProductItem.addActionListener(e -> showProductList());

        JMenuItem showCartItem = new JMenuItem("장바구니 보기");
        cartMenu.add(showCartItem);
        showCartItem.addActionListener(e -> showCart());

        JMenuItem showOrderItem = new JMenuItem("주문 내역 보기");
        orderMenu.add(showOrderItem);
        showOrderItem.addActionListener(e -> showOrderHistory());

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btn1 = new JButton("1.顾客信息确认");
        JButton btn2 = new JButton("2.购物车商品目录");
        JButton btn3 = new JButton("3.购物车删除");
        JButton btn4 = new JButton("4.购物车项目数量增加");
        JButton btn5 = new JButton("5.购物车项目数量减少");
        JButton btn6 = new JButton("6.购物车项目删除");
        JButton btn7 = new JButton("7.立即订购");
        JButton btn8 = new JButton("8.结束");
        JButton btn9 = new JButton("9.管理者");

        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);
        buttonPanel.add(btn4);
        buttonPanel.add(btn5);
        buttonPanel.add(btn6);
        buttonPanel.add(btn7);
        buttonPanel.add(btn8);
        buttonPanel.add(btn9);

        f2.add(buttonPanel, BorderLayout.CENTER);

        btn1.addActionListener(e -> confirmCustomerInfo());
        btn2.addActionListener(e -> showProductListForCart());
        btn3.addActionListener(e -> clearCart());
        btn4.addActionListener(e -> increaseCartItemQuantity());
        btn5.addActionListener(e -> decreaseCartItemQuantity());
        btn6.addActionListener(e -> removeCartItem());
        btn7.addActionListener(e -> placeOrder());
        btn8.addActionListener(e -> System.exit(0));
        btn9.addActionListener(e -> {
            if (isAdmin) {
                adminPanel();
            } else {
                JOptionPane.showMessageDialog(f2, "관리자 권한이 필요합니다.");
            }
        });

        f2.setLocationRelativeTo(mainFrame);
        f2.setVisible(true);
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
