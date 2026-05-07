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
        products.add(new Product(2, "만년필", 1000));
        products.add(new Product(3, "책가방", 30000));
        products.add(new Product(4, "계산기", 8000));
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

        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btn1 = new JButton("1.고객 정보 확인");
        JButton btn2 = new JButton("2.장바구니 상품 목록");
        JButton btn3 = new JButton("3.장바구니 삭제");
        JButton btn4 = new JButton("4.장바구니 상품 수량 증가");
        JButton btn5 = new JButton("5.장바구니 상품 수량 감소");
        JButton btn6 = new JButton("6.장바구니 상품 삭제");
        JButton btn7 = new JButton("7.즉시 주문");
        JButton btn8 = new JButton("8.종료");
        JButton btn9 = new JButton("9.관리자");

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

    // ------------------- 功能实现 -------------------

    private void confirmCustomerInfo() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 주문할 수 없습니다.");
            return;
        }
        if (currentUser == null) return;

        JFrame f3 = new JFrame();
        f3.setTitle("고객 정보");
        f3.setSize(500, 400);
        f3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f3.setLayout(new FlowLayout());

        String name = nameField.getText();
        String phone = phoneField.getText();

        JLabel infoLabel = new JLabel("정보가 정확합니까?");
        JRadioButton correctBtn = new JRadioButton("맞습니다.", true);
        JRadioButton incorrectBtn = new JRadioButton("올바르지 않습니다.");
        ButtonGroup group = new ButtonGroup();
        group.add(correctBtn);
        group.add(incorrectBtn);

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("고객명:"));
        JTextField nameTf = new JTextField(name, 15);
        nameTf.setEditable(false);
        namePanel.add(nameTf);

        JPanel phonePanel = new JPanel();
        phonePanel.add(new JLabel("연락처:"));
        JTextField phoneTf = new JTextField(phone, 15);
        phoneTf.setEditable(false);
        phonePanel.add(phoneTf);

        JPanel addrPanel = new JPanel();
        addrPanel.add(new JLabel("배송 주소:"));
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

        JLabel title = new JLabel("----------고객 정보----------");
        JLabel info = new JLabel("顾客名:" + name + "                  " + "연락처:" + phone);
        JLabel addrInfo = new JLabel("配送地址:" + addr + "                  " + "배송일:" +
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

    private void removeCartItem() {
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
        String selected = (String) JOptionPane.showInputDialog(null, "삭제할 상품을 선택하세요:", "상품 삭제",
                JOptionPane.QUESTION_MESSAGE, null, itemNames, itemNames[0]);
        if (selected != null) {
            cart.removeItem(selected);
            userManager.saveUsers();
            JOptionPane.showMessageDialog(null, "삭제되었습니다.");
        }
    }

    private void placeOrder() {
        if (isAdmin) {
            JOptionPane.showMessageDialog(null, "관리자는 주문할 수 없습니다.");
            return;
        }
        if (currentUser == null) return;
        CustomerInfo ci = currentUser.getCustomerInfo();
        if (ci == null || ci.getName() == null || ci.getName().isEmpty()) {
            JOptionPane.showMessageDialog(null, "고객 정보를 먼저 입력해주세요.");
            return;
        }
        ShoppingCart cart = currentUser.getShoppingCart();
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, "장바구니가 비어 있습니다.");
            return;
        }

        Order order = new Order(ci, cart.getItemsCopy(), new Date());
        currentUser.getOrders().add(order);
        cart.clear();
        userManager.saveUsers();

        StringBuilder sb = new StringBuilder("주문이 완료되었습니다.\n");
        sb.append("고객: ").append(ci.getName()).append("\n");
        sb.append("주소: ").append(ci.getAddress()).append("\n");
        sb.append("주문 상품:\n");
        for (CartItem item : order.getItems()) {
            sb.append("  ").append(item.getProduct().getName()).append(" x ").append(item.getQuantity())
                    .append(" = ").append(item.getTotalPrice()).append("원\n");
        }
        sb.append("총액: ").append(order.getTotalAmount()).append("원\n");
        JOptionPane.showMessageDialog(null, sb.toString(), "주문 완료", JOptionPane.INFORMATION_MESSAGE);
    }

    private void adminPanel() {
        JFrame adminFrame = new JFrame("관리자 모드 - 상품 및 사용자 관리");
        adminFrame.setSize(600, 500);
        adminFrame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // 商品管理
        JPanel productPanel = new JPanel(new BorderLayout());
        DefaultListModel<Product> productListModel = new DefaultListModel<>();
        for (Product p : products) {
            productListModel.addElement(p);
        }
        JList<Product> productList = new JList<>(productListModel);
        productList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Product p = (Product) value;
                return super.getListCellRendererComponent(list, p.getId() + ". " + p.getName() + " - " + p.getPrice() + "원", index, isSelected, cellHasFocus);
            }
        });
        JScrollPane productScroll = new JScrollPane(productList);
        productPanel.add(productScroll, BorderLayout.CENTER);

        JPanel productButtonPanel = new JPanel();
        JButton addBtn = new JButton("상품 추가");
        JButton editBtn = new JButton("상품 수정");
        JButton deleteBtn = new JButton("상품 삭제");
        productButtonPanel.add(addBtn);
        productButtonPanel.add(editBtn);
        productButtonPanel.add(deleteBtn);
        productPanel.add(productButtonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("상품 이름:");
            if (name == null || name.trim().isEmpty()) return;
            String priceStr = JOptionPane.showInputDialog("상품 가격:");
            if (priceStr == null) return;
            try {
                int price = Integer.parseInt(priceStr);
                int newId = products.stream().mapToInt(Product::getId).max().orElse(0) + 1;
                Product newProduct = new Product(newId, name, price);
                products.add(newProduct);
                productListModel.addElement(newProduct);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(adminFrame, "올바른 가격을 입력하세요.");
            }
        });

        editBtn.addActionListener(e -> {
            Product selected = productList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(adminFrame, "수정할 상품을 선택하세요.");
                return;
            }
            String newName = JOptionPane.showInputDialog("새 이름:", selected.getName());
            if (newName != null && !newName.trim().isEmpty()) {
                selected.setName(newName);
            }
            String newPriceStr = JOptionPane.showInputDialog("새 가격:", selected.getPrice());
            if (newPriceStr != null) {
                try {
                    int newPrice = Integer.parseInt(newPriceStr);
                    selected.setPrice(newPrice);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(adminFrame, "올바른 가격을 입력하세요.");
                }
            }
            productList.repaint();
        });

        deleteBtn.addActionListener(e -> {
            Product selected = productList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(adminFrame, "삭제할 상품을 선택하세요.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(adminFrame, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                products.remove(selected);
                productListModel.removeElement(selected);
            }
        });

        // 用户管理
        JPanel userPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> userListModel = new DefaultListModel<>();
        for (User u : userManager.getUsers()) {
            userListModel.addElement(u.getUsername());
        }
        JList<String> userList = new JList<>(userListModel);
        JScrollPane userScroll = new JScrollPane(userList);
        userPanel.add(userScroll, BorderLayout.CENTER);

        JButton deleteUserBtn = new JButton("선택한 사용자 삭제");
        userPanel.add(deleteUserBtn, BorderLayout.SOUTH);

        deleteUserBtn.addActionListener(e -> {
            String selectedUsername = userList.getSelectedValue();
            if (selectedUsername == null) {
                JOptionPane.showMessageDialog(adminFrame, "삭제할 사용자를 선택하세요.");
                return;
            }
            if (currentUser != null && selectedUsername.equals(currentUser.getUsername())) {
                JOptionPane.showMessageDialog(adminFrame, "현재 로그인한 사용자는 삭제할 수 없습니다.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(adminFrame, "사용자 '" + selectedUsername + "'를(을) 삭제하시겠습니까?\n이 작업은 되돌릴 수 없습니다.", "사용자 삭제", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userManager.deleteUser(selectedUsername);
                userListModel.removeElement(selectedUsername);
                JOptionPane.showMessageDialog(adminFrame, "사용자가 삭제되었습니다.");
            }
        });

        tabbedPane.addTab("상품 관리", productPanel);
        tabbedPane.addTab("사용자 관리", userPanel);

        adminFrame.add(tabbedPane);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

    // ------------------- 内部数据类 -------------------
    static class Product implements Serializable {
        private int id;
        private String name;
        private int price;
        public Product(int id, String name, int price) {
            this.id = id; this.name = name; this.price = price;
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public int getPrice() { return price; }
        public void setName(String name) { this.name = name; }
        public void setPrice(int price) { this.price = price; }
    }

    static class CartItem implements Serializable {
        private Product product;
        private int quantity;
        public CartItem(Product product, int quantity) {
            this.product = product; this.quantity = quantity;
        }
        public Product getProduct() { return product; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public int getTotalPrice() { return product.getPrice() * quantity; }
    }

    static class ShoppingCart implements Serializable {
        private List<CartItem> items = new ArrayList<>();
        public void addItem(Product product, int quantity) {
            for (CartItem item : items) {
                if (item.getProduct().getId() == product.getId()) {
                    item.setQuantity(item.getQuantity() + quantity);
                    return;
                }
            }
            items.add(new CartItem(product, quantity));
        }
        public void increaseQuantity(String productName) {
            for (CartItem item : items) {
                if (item.getProduct().getName().equals(productName)) {
                    item.setQuantity(item.getQuantity() + 1);
                    return;
                }
            }
        }
        public void decreaseQuantity(String productName) {
            for (CartItem item : items) {
                if (item.getProduct().getName().equals(productName)) {
                    if (item.getQuantity() > 1) {
                        item.setQuantity(item.getQuantity() - 1);
                    } else {
                        items.remove(item);
                    }
                    return;
                }
            }
        }
        public void removeItem(String productName) {
            items.removeIf(item -> item.getProduct().getName().equals(productName));
        }
        public void clear() { items.clear(); }
        public boolean isEmpty() { return items.isEmpty(); }
        public List<CartItem> getItems() { return items; }
        public List<CartItem> getItemsCopy() { return new ArrayList<>(items); }
        public int getTotalPrice() { return items.stream().mapToInt(CartItem::getTotalPrice).sum(); }
    }

    static class CustomerInfo implements Serializable {
        private String name, phone, address;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    static class Order implements Serializable {
        private CustomerInfo customer;
        private List<CartItem> items;
        private Date orderDate;
        public Order(CustomerInfo customer, List<CartItem> items, Date orderDate) {
            this.customer = customer; this.items = items; this.orderDate = orderDate;
        }
        public String getCustomerName() { return customer.getName(); }
        public List<CartItem> getItems() { return items; }
        public Date getOrderDate() { return orderDate; }
        public int getTotalAmount() { return items.stream().mapToInt(CartItem::getTotalPrice).sum(); }
    }

    static class User implements Serializable {
        private String username;
        private String password;
        private CustomerInfo customerInfo;
        private ShoppingCart shoppingCart;
        private List<Order> orders;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.customerInfo = new CustomerInfo();
            this.shoppingCart = new ShoppingCart();
            this.orders = new ArrayList<>();
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public CustomerInfo getCustomerInfo() { return customerInfo; }
        public void setCustomerInfo(CustomerInfo customerInfo) { this.customerInfo = customerInfo; }
        public ShoppingCart getShoppingCart() { return shoppingCart; }
        public List<Order> getOrders() { return orders; }
    }

    static class UserManager {
        private static final String USER_FILE = "users.dat";
        private List<User> users;

        @SuppressWarnings("unchecked")
        public UserManager() {
            loadUsers();
        }

        private void loadUsers() {
            File file = new File(USER_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    users = (List<User>) ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                    users = new ArrayList<>();
                }
            } else {
                users = new ArrayList<>();
            }
        }

        public void saveUsers() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
                oos.writeObject(users);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean register(String username, String password) {
            if (findUser(username) != null) return false;
            users.add(new User(username, password));
            saveUsers();
            return true;
        }

        public User login(String username, String password) {
            User u = findUser(username);
            if (u != null && u.getPassword().equals(password)) {
                return u;
            }
            return null;
        }

        public void deleteUser(String username) {
            users.removeIf(u -> u.getUsername().equals(username));
            saveUsers();
        }

        private User findUser(String username) {
            for (User u : users) {
                if (u.getUsername().equals(username)) return u;
            }
            return null;
        }

        public List<User> getUsers() {
            return users;
        }
    }

  
    static class LoginDialog extends JDialog {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private User loggedInUser;
        private boolean isAdminLogged = false;

        public LoginDialog(JFrame parent) {
            super(parent, "로그인 / 회원가입", true); 
            setSize(400, 250);
            setLayout(new GridLayout(4, 2, 10, 10));
            setLocationRelativeTo(parent);

            add(new JLabel("사용자 이름:"));
            usernameField = new JTextField();
            add(usernameField);

            add(new JLabel("비밀번호:"));
            passwordField = new JPasswordField();
            add(passwordField);

            JButton loginBtn = new JButton("로그인");
            JButton registerBtn = new JButton("회원가입");

            add(loginBtn);
            add(registerBtn);

            loginBtn.addActionListener(e -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if ("wz521".equals(username) && "666666".equals(password)) {
                    isAdminLogged = true;
                    loggedInUser = null;
                    dispose();
                    return;
                }
                User user = userManager.login(username, password);
                if (user != null) {
                    loggedInUser = user;
                    isAdminLogged = false;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "사용자 이름 또는 비밀번호가 잘못되었습니다.");
                }
            });

            registerBtn.addActionListener(e -> {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "사용자 이름과 비밀번호를 입력하세요.");
                    return;
                }
                if (userManager.register(username, password)) {
                    JOptionPane.showMessageDialog(this, "회원가입 성공! 로그인하세요.");
                } else {
                    JOptionPane.showMessageDialog(this, "이미 존재하는 사용자 이름입니다.");
                }
            });
        }

        public User getLoggedInUser() { return loggedInUser; }
        public boolean isAdmin() { return isAdminLogged; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummy = new JFrame();
            dummy.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            LoginDialog loginDlg = new LoginDialog(dummy);
            loginDlg.setVisible(true); 

            if (loginDlg.getLoggedInUser() != null || loginDlg.isAdmin()) {
                new oss(loginDlg.getLoggedInUser(), loginDlg.isAdmin());
            } else {
                System.exit(0);
            }
        });
    }
