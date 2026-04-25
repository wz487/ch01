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
    private void confirmCustomerInfo() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 주문할 수 없습니다.");
            return;
        }
        if (currentUser == null) return;

        JFrame f3 = new JFrame();
        f3.setTitle("고객정보");
        f3.setSize(500, 400);
        f3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f3.setLayout(new FlowLayout());

        String name = nameField.getText();
        String phone = phoneField.getText();

        JLabel infoLabel = new JLabel("信息是否正确");
        JRadioButton correctBtn = new JRadioButton("正确", true);
        JRadioButton incorrectBtn = new JRadioButton("不正确");
        ButtonGroup group = new ButtonGroup();
        group.add(correctBtn);
        group.add(incorrectBtn);

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("성명:"));
        JTextField nameTf = new JTextField(name, 15);
        nameTf.setEditable(false);
        namePanel.add(nameTf);

        JPanel phonePanel = new JPanel();
        phonePanel.add(new JLabel("联系方式:"));
        JTextField phoneTf = new JTextField(phone, 15);
        phoneTf.setEditable(false);
        phonePanel.add(phoneTf);

        JPanel addrPanel = new JPanel();
        addrPanel.add(new JLabel("配送地址:"));
        JTextField addrTf = new JTextField(15);
        addrPanel.add(addrTf);

        JButton submitBtn = new JButton("주문완료");

        f3.add(infoLabel);
        f3.add(correctBtn);
        f3.add(incorrectBtn);
        f3.add(namePanel);
        f3.add(phonePanel);
        f3.add(addrPanel);
        f3.add(submitBtn);

        incorrectBtn.addActionListener(e -> {
            nameTf.setEditable(true);
            phoneTf.setEditable(true);
        });
        correctBtn.addActionListener(e -> {
            nameTf.setEditable(false);
            phoneTf.setEditable(false);
        });

        submitBtn.addActionListener(e -> {
            String finalName = nameTf.getText();
            String finalPhone = phoneTf.getText();
            String finalAddr = addrTf.getText();

            CustomerInfo ci = currentUser.getCustomerInfo();
            if (ci == null) {
                ci = new CustomerInfo();
                currentUser.setCustomerInfo(ci);
            }
            ci.setName(finalName);
            ci.setPhone(finalPhone);
            ci.setAddress(finalAddr);

            userManager.saveUsers();

            showCustomerInfoDetail(finalName, finalPhone, finalAddr);
            f3.dispose();
        });

        f3.setLocationRelativeTo(null);
        f3.setVisible(true);
    }
    private void showCustomerInfoDetail(String name, String phone, String addr) {
        JFrame f4 = new JFrame();
        f4.setTitle("顾客信息");
        f4.setSize(500, 300);
        f4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f4.setLayout(new FlowLayout());

        JLabel title = new JLabel("----------고객정보----------");
        JLabel info = new JLabel("성몀:" + name + "                  " + "联系方式:" + phone);
        JLabel addrInfo = new JLabel("配送地址:" + addr + "                  " + "配送日:" +
                Calendar.getInstance().get(Calendar.MONTH) + "/" +
                Calendar.getInstance().get(Calendar.DATE) + "/" +
                Calendar.getInstance().get(Calendar.YEAR));

        f4.add(title);
        f4.add(info);
        f4.add(addrInfo);
        f4.pack();
        f4.setLocationRelativeTo(null);
        f4.setVisible(true);
    }
    private void showCustomerInfo() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자 모드에서는 고객 정보가 없습니다.");
            return;
        }
        if (currentUser == null) return;
        CustomerInfo ci = currentUser.getCustomerInfo();
        if (ci == null || ci.getName() == null || ci.getName().isEmpty()) {
            JOptionPane.showMessageDialog(null, "고객 정보가 없습니다. 먼저 정보를 입력해주세요.");
        } else {
            JOptionPane.showMessageDialog(null,
                    "이름: " + ci.getName() + "\n연락처: " + ci.getPhone() + "\n주소: " + ci.getAddress(),
                    "고객 정보", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showProductList() {
        StringBuilder sb = new StringBuilder("상품 목록:\n");
        for (Product p : products) {
            sb.append(p.getId()).append(". ").append(p.getName()).append(" - ").append(p.getPrice()).append("원\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "상품 목록", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showCart() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 장바구니를 사용할 수 없습니다.");
            return;
        }
        if (currentUser == null) return;
        ShoppingCart cart = currentUser.getShoppingCart();
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "장바구니가 비어 있습니다.");
        } else {
            StringBuilder sb = new StringBuilder("장바구니 내용:\n");
            for (CartItem item : cart.getItems()) {
                sb.append(item.getProduct().getName()).append(" x ").append(item.getQuantity())
                        .append(" = ").append(item.getTotalPrice()).append("원\n");
            }
            sb.append("총액: ").append(cart.getTotalPrice()).append("원");
            JOptionPane.showMessageDialog(null, sb.toString(), "장바구니", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void showOrderHistory() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 주문 내역이 없습니다.");
            return;
        }
        if (currentUser == null) return;
        List<Order> orders = currentUser.getOrders();
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(null, "주문 내역이 없습니다.");
        } else {
            StringBuilder sb = new StringBuilder("주문 내역:\n");
            for (Order order : orders) {
                sb.append("주문일: ").append(order.getOrderDate()).append("\n");
                sb.append("고객: ").append(order.getCustomerName()).append("\n");
                sb.append("총액: ").append(order.getTotalAmount()).append("원\n");
                sb.append("상품:\n");
                for (CartItem item : order.getItems()) {
                    sb.append("  ").append(item.getProduct().getName()).append(" x ").append(item.getQuantity())
                            .append(" = ").append(item.getTotalPrice()).append("원\n");
                }
                sb.append("-------------------\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "주문 내역", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void showProductListForCart() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 상품을 장바구니에 추가할 수 없습니다.");
            return;
        }
        if (currentUser == null) return;

        JFrame dialog = new JFrame("상품 선택");
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        DefaultListModel<Product> listModel = new DefaultListModel<>();
        for (Product p : products) {
            listModel.addElement(p);
        }
        JList<Product> productList = new JList<>(listModel);
        productList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Product p = (Product) value;
                return super.getListCellRendererComponent(list, p.getName() + " - " + p.getPrice() + "원", index, isSelected, cellHasFocus);
            }
        });
        JScrollPane scrollPane = new JScrollPane(productList);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JLabel quantityLabel = new JLabel("수량:");
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JButton addButton = new JButton("장바구니에 추가");
        controlPanel.add(quantityLabel);
        controlPanel.add(quantitySpinner);
        controlPanel.add(addButton);
        dialog.add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            Product selected = productList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(dialog, "상품을 선택하세요.");
                return;
            }
            int quantity = (Integer) quantitySpinner.getValue();
            currentUser.getShoppingCart().addItem(selected, quantity);
            userManager.saveUsers();
            JOptionPane.showMessageDialog(dialog, "추가되었습니다.");
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    private void clearCart() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 장바구니를 사용할 수 없습니다.");
            return;
        }
        if (currentUser == null) return;
        currentUser.getShoppingCart().clear();
        userManager.saveUsers();
        JOptionPane.showMessageDialog(null, "장바구니가 비워졌습니다.");
    }

    private void increaseCartItemQuantity() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 장바구니를 사용할 수 없습니다.");
            return;
        }
        if (currentUser == null) return;
        ShoppingCart cart = currentUser.getShoppingCart();
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "장바구니가 비어 있습니다.");
            return;
        }
        String[] itemNames = cart.getItems().stream().map(item -> item.getProduct().getName()).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(null, "수량을 증가시킬 상품을 선택하세요:", "수량 증가",
                JOptionPane.QUESTION_MESSAGE, null, itemNames, itemNames[0]);
        if (selected != null) {
            cart.increaseQuantity(selected);
            userManager.saveUsers();
            JOptionPane.showMessageDialog(null, "수량이 증가되었습니다.");
        }
    }
    private void decreaseCartItemQuantity() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 장바구니를 사용할 수 없습니다.");
            return;
        }
        if (currentUser == null) return;
        ShoppingCart cart = currentUser.getShoppingCart();
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "장바구니가 비어 있습니다.");
            return;
        }
        String[] itemNames = cart.getItems().stream().map(item -> item.getProduct().getName()).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(null, "수량을 감소시킬 상품을 선택하세요:", "수량 감소",
                JOptionPane.QUESTION_MESSAGE, null, itemNames, itemNames[0]);
        if (selected != null) {
            cart.decreaseQuantity(selected);
            userManager.saveUsers();
            JOptionPane.showMessageDialog(null, "수량이 감소되었습니다.");
        }
    }


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
