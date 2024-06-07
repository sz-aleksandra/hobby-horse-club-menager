package pap.gui;

import lombok.Getter;
import pap.db.dao.OfferDAO;
import pap.db.entities.Discount;
import pap.db.entities.Offer;
import pap.db.entities.PaymentMethod;
import pap.gui.FormGUITemplate;

import pap.db.dao.PaymentMethodDAO;
import pap.db.dao.DiscountsDAO;
import pap.gui.HomePageGUI;
import pap.gui.components.OfferPanel;
import pap.gui.components.RoundedButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.io.File;
import java.util.HashMap;
import java.util.Calendar;


public class MakeReservationPanel extends JPanel {
    int panelWidth, panelHeight, userId;
    private JLabel costLabel;
    private JComboBox<Integer> startYearComboBox;
    private JComboBox<Integer> startMonthComboBox;
    private JComboBox<Integer> startDayComboBox;

    private JComboBox<Integer> endYearComboBox;
    private JComboBox<Integer> endMonthComboBox;
    private JComboBox<Integer> endDayComboBox;
    private JFrame frame;
    @Getter
    PaymentMethod pickedCreditCard;
    Color bgColor; Font fontBigger, fontMiddle, fontMiddleBold, fontSmaller, fontSmallerBold;
    LocalDate startDate, endDate;
    String userType;
    Integer offerId;
    Float discountValue;
    Discount discount;
    @Getter
    private String discountCode;

    public MakeReservationPanel(Color bgColor, Font fontBigger, Font fontBiggerBold, Font fontMiddle, Font fontMiddleBold, Font fontSmaller, Font fontSmallerBold, int panelWidth, int panelHeight,
                             HashMap<String, String> offerInfo, HashMap<String, String> reservationInfo, Integer userId, JFrame frame, String userType, Integer offerId) {

        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.bgColor = bgColor;
        this.fontBigger = fontBigger;
        this.fontMiddle = fontMiddle;
        this.fontMiddleBold = fontMiddleBold;
        this.fontSmaller = fontSmaller;
        this.fontSmallerBold = fontSmallerBold;
        int topPanelHeight = panelHeight / 6;
        this.userId = userId;
        this.frame = frame;
        this.userType = userType;
        this.pickedCreditCard = null;
        this.offerId = offerId;
        this.discountValue = 0f;
        this.discount = null;

        setBackground(bgColor);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setMaximumSize(new Dimension(panelWidth, panelHeight));

        this.costLabel = new JLabel("Cost: 0.00 zł");

        JPanel topPanel = new JPanel();
        topPanel.setBackground(bgColor);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.setPreferredSize(new Dimension(panelWidth, topPanelHeight));
        topPanel.setMaximumSize(new Dimension(panelWidth, topPanelHeight));
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel offerTitle1 = new JLabel(offerInfo.get("name"), JLabel.LEFT);
        offerTitle1.setFont(fontBiggerBold);
        JLabel offerTitle2 = new JLabel(" by ", JLabel.LEFT);
        offerTitle2.setFont(fontBigger);
        JLabel offerTitle3 = new JLabel(offerInfo.get("hotel"), JLabel.LEFT);
        offerTitle3.setFont(fontBiggerBold);

        topPanel.add(offerTitle1);
        topPanel.add(offerTitle2);
        topPanel.add(offerTitle3);
        topPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        add(topPanel);

        int contentPanelsHeight = panelHeight - topPanelHeight;
        JPanel groupPanel1 = new JPanel();
        groupPanel1.setBackground(bgColor);
        groupPanel1.setLayout(new BoxLayout(groupPanel1, BoxLayout.LINE_AXIS));
        groupPanel1.setPreferredSize(new Dimension(panelWidth, contentPanelsHeight));
        groupPanel1.setMaximumSize(new Dimension(panelWidth, contentPanelsHeight));
        groupPanel1.add(Box.createRigidArea(new Dimension(10, 0)));

        int leftPanelWidth = panelWidth * 4 / 10;
        JPanel contentPanelLeft = new JPanel();
        contentPanelLeft.setBackground(bgColor);
        contentPanelLeft.setLayout(new BoxLayout(contentPanelLeft, BoxLayout.PAGE_AXIS));
        contentPanelLeft.setPreferredSize(new Dimension(leftPanelWidth, contentPanelsHeight));
        contentPanelLeft.setMaximumSize(new Dimension(leftPanelWidth, contentPanelsHeight));
        groupPanel1.add(contentPanelLeft);
        groupPanel1.add(createSeparator(50, 600), BorderLayout.CENTER);
        JPanel contentPanelRight = new JPanel();
        contentPanelRight.setBackground(bgColor);
        contentPanelRight.setLayout(new BoxLayout(contentPanelRight, BoxLayout.PAGE_AXIS));
        contentPanelRight.setPreferredSize(new Dimension(panelWidth - leftPanelWidth, contentPanelsHeight));
        contentPanelRight.setMaximumSize(new Dimension(panelWidth - leftPanelWidth, contentPanelsHeight));
        groupPanel1.add(contentPanelRight);
        add(groupPanel1);
        groupPanel1.add(Box.createRigidArea(new Dimension(10, 0)));

        // LEFT PANEL
        int offerImgWidth = leftPanelWidth ;
        int offerImgHeight = contentPanelsHeight / 2;
        try {
            Image offerImg = new OfferDAO().getImageById(offerId);
            offerImg = offerImg.getScaledInstance(offerImgWidth, offerImgHeight, Image.SCALE_SMOOTH);
            ImageIcon offerImgIcon = new ImageIcon(offerImg);
            JLabel offerImgLabel = new JLabel();
            offerImgLabel.setIcon(offerImgIcon);
            contentPanelLeft.add(offerImgLabel);
        } catch (Exception e) {
            JPanel imgPanel = new JPanel();
            imgPanel.setBackground(Color.RED);
            imgPanel.setPreferredSize(new Dimension(offerImgWidth, offerImgHeight));
            imgPanel.setMaximumSize(new Dimension(offerImgWidth, offerImgHeight));
            contentPanelLeft.add(imgPanel);
        }
        contentPanelLeft.add(Box.createRigidArea(new Dimension(0, contentPanelsHeight / 30)));

        // Start Date
        JPanel startDatePanel = new JPanel();
        startDatePanel.setBackground(bgColor);
        startDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel startDateLabel = new JLabel("Reservation Start Date:");
        startDateLabel.setFont(new Font(startDateLabel.getFont().getName(), Font.BOLD, 16));
        startYearComboBox = createComboBox(getCurrentYear(), getCurrentYear()+1);
        startMonthComboBox = createComboBox(1, 12);
        startDayComboBox = new JComboBox<>();

        JLabel blankLabel = new JLabel("ㅤ ");
        contentPanelLeft.add(blankLabel);

        JLabel dayStartLabel = new JLabel("ㅤ             Day:");
        JLabel monthStartLabel = new JLabel("Month:");
        JLabel yearStartLabel = new JLabel("Year:");
        JLabel dayEndLabel = new JLabel("ㅤ             Day:");
        JLabel monthEndLabel = new JLabel("Month:");
        JLabel yearEndLabel = new JLabel("Year:");

        contentPanelLeft.add(startDateLabel);
        startDatePanel.add(dayStartLabel);
        startDatePanel.add(startDayComboBox);
        startDatePanel.add(monthStartLabel);
        startDatePanel.add(startMonthComboBox);
        startDatePanel.add(yearStartLabel);
        startDatePanel.add(startYearComboBox);
        contentPanelLeft.add(startDatePanel);

        // End Date
        JPanel endDatePanel = new JPanel();
        endDatePanel.setBackground(bgColor);
        endDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel endDateLabel = new JLabel("Reservation End Date:  ");
        endDateLabel.setFont(new Font(endDateLabel.getFont().getName(), Font.BOLD, 16));
        endYearComboBox = createComboBox(getCurrentYear(), getCurrentYear()+1);
        endMonthComboBox = createComboBox(1, 12);
        endDayComboBox = new JComboBox<>();

        contentPanelLeft.add(endDateLabel);
        endDatePanel.add(dayEndLabel);
        endDatePanel.add(endDayComboBox);
        endDatePanel.add(monthEndLabel);
        endDatePanel.add(endMonthComboBox);
        endDatePanel.add(yearEndLabel);
        endDatePanel.add(endYearComboBox);
        contentPanelLeft.add(endDatePanel);

        startMonthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDays(startYearComboBox, startMonthComboBox, startDayComboBox);
                updateCost();
            }
        });
        startYearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDays(startYearComboBox, startMonthComboBox, startDayComboBox);
                updateCost();
            }
        });
        startDayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCost();
            }
        });

        endMonthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDays(endYearComboBox, endMonthComboBox, endDayComboBox);
                updateCost();
            }
        });
        endYearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDays(endYearComboBox, endMonthComboBox, endDayComboBox);
                updateCost();
            }
        });
        endDayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCost();
            }
        });

        updateDays(startYearComboBox, startMonthComboBox, startDayComboBox);
        updateDays(endYearComboBox, endMonthComboBox, endDayComboBox);

        // RIGHT PANEL
        contentPanelRight.setLayout(new BoxLayout(contentPanelRight, BoxLayout.Y_AXIS));
        contentPanelRight.revalidate();
        JLabel paymentMethodLabel = new JLabel("Choose Payment Method:");
        paymentMethodLabel.setFont(new Font(endDateLabel.getFont().getName(), Font.BOLD, 24));
        contentPanelRight.add(paymentMethodLabel);

        addCreditCardPanel(contentPanelRight);
        addDiscountPanel(contentPanelRight);
        addReservationCostPanel(contentPanelRight);


    }

    private JComboBox<Integer> createComboBox(int start, int end) {
        Integer[] items = new Integer[end - start + 1];
        for (int i = 0; i < items.length; i++) {
            items[i] = start + i;
        }
        return new JComboBox<>(items);
    }

    private int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    private void updateDays(JComboBox<Integer> yearComboBox, JComboBox<Integer> monthComboBox, JComboBox<Integer> dayComboBox) {
        int year = (int) yearComboBox.getSelectedItem();
        int month = (int) monthComboBox.getSelectedItem();

        Calendar currentDate = Calendar.getInstance();
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1; // Calendar months are 0-based
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);

        if (year == currentYear && month == currentMonth) {
            // If the selected month is the current month, filter out days earlier than the current day + 3
            currentDate.add(Calendar.DAY_OF_MONTH, 3);
            int minSelectableDay = currentDate.get(Calendar.DAY_OF_MONTH);

            dayComboBox.removeAllItems();
            for (int i = minSelectableDay; i <= getDaysInMonth(year, month); i++) {
                dayComboBox.addItem(i);
            }
        } else {
            // For future months, include all days
            int daysInMonth = getDaysInMonth(year, month);

            dayComboBox.removeAllItems();
            for (int i = 1; i <= daysInMonth; i++) {
                dayComboBox.addItem(i);
            }
        }
    }

    private int getDaysInMonth(int year, int month) {
        int daysInMonth;
        if (month == 2) {
            daysInMonth = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else {
            daysInMonth = 31;
        }
        return daysInMonth;
    }


    void addCreditCardPanel(JPanel panel) {
        List<PaymentMethod> creditCards = getCreditCards();

        if (creditCards.isEmpty()) {
            JLabel addCreditCardLabel = new JLabel("You have no credit cards. Add a new one.");
            addCreditCardLabel.setFont(new Font(addCreditCardLabel.getFont().getName(), Font.BOLD, 16));
            RoundedButton addCreditCardButton = new RoundedButton("Go to your credit cards.",
                    panelWidth*5/21, panelHeight/10, Color.ORANGE, Color.YELLOW, fontMiddle, false );

            panel.add(addCreditCardLabel);
            panel.add(addCreditCardButton);

            addCreditCardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    new HomePageGUI(userId, userType).createGUI();
                }
            });
        } else {
            JLabel selectCreditCardLabel = new JLabel("Select a credit card:");
            selectCreditCardLabel.setFont(new Font(selectCreditCardLabel.getFont().getName(), Font.BOLD, 16));
            panel.add(selectCreditCardLabel);

            ButtonGroup cardButtonGroup = new ButtonGroup();
            Integer counter = 1;
            for (PaymentMethod creditCard : creditCards) {
                JLabel cardCounter = new JLabel("Credit Card" + counter + ": ");
                cardCounter.setFont(new Font(selectCreditCardLabel.getFont().getName(), Font.PLAIN, 16));
                counter += 1;
                String cardNumber = creditCard.getCardNumber();
                cardNumber = cardNumber.substring(0, 4) + "-" + "#".repeat(cardNumber.length() - 8) + "-" + cardNumber.substring(cardNumber.length() - 4);
                JRadioButton creditCardRadioButton = new JRadioButton(cardNumber);
                creditCardRadioButton.setFont(new Font(selectCreditCardLabel.getFont().getName(), Font.BOLD, 16));

                creditCardRadioButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pickedCreditCard = creditCard;
                        System.out.println("Selected Credit Card: " + creditCard.getCardNumber());
                    }
                });
                panel.add(cardCounter);
                cardButtonGroup.add(creditCardRadioButton);
                panel.add(creditCardRadioButton);
            }
        }
    }

    void addDiscountPanel(JPanel panel) {
        // Use a smaller font size for the label
        JPanel newPanel = new JPanel();
        JLabel addCreditCardLabel = new JLabel("Enter discount code:");
        addCreditCardLabel.setFont(new Font(addCreditCardLabel.getFont().getName(), Font.PLAIN, 14));

        JTextField discountCodeField = new JTextField(16);

        // Smaller font size for the button
        RoundedButton checkDiscountCodeButton = new RoundedButton("Check",
                panelWidth * 5 / 6, panelHeight / 4, Color.ORANGE, Color.YELLOW, new Font("Arial", Font.PLAIN, 12), false);

        // Set the layout manager to GridBagLayout
        newPanel.setBackground(bgColor);
        newPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        newPanel.add(addCreditCardLabel, gbc);

        // Text field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        newPanel.add(discountCodeField, gbc);

        // Button
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        newPanel.add(checkDiscountCodeButton, gbc);
        panel.add(newPanel);

        checkDiscountCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String discountCode = discountCodeField.getText();
                float discountValid = isValidDiscountCode(discountCode);
                if (discountValid > 0) {
                    if (discount.getValueType() == 0){
                        JOptionPane.showMessageDialog(null, String.format("Discount code correct, adding %szł discount", String.format("%.2f", discountValue)), "Discount Code Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, String.format("Discount code correct, adding %s%% discount", String.format("%.2f", discountValue)), "Discount Code Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Discount code incorrect!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private float isValidDiscountCode(String discountCode) {
        List<Discount> discounts = new DiscountsDAO().findAll();
        Offer offer = new OfferDAO().findById(offerId);
        for (Discount discount: discounts){
            if (isCurrentDiscountValid(offer, discount, discountCode)){
                this.discountValue = discount.getValue();
                this.discount = discount;
                updateCost();
                return discount.getValue();
            }
        }
        return 0;
    }

    private boolean isCurrentDiscountValid(Offer offer, Discount discount, String discountCode){
        return discount.isActive() &&
                (offer.getHotel().getHotelId() == discount.getHotel().getHotelId() || discount.getType() == 0) &&
                discountCode.equalsIgnoreCase(discount.getCode());
    }

    List<PaymentMethod> getCreditCards(){
        return new PaymentMethodDAO().findByClientId(userId);
    }

    private JPanel createSeparator(int w, int h) {
        JPanel separatorPanel = new JPanel();
        separatorPanel.setBackground(bgColor);
        separatorPanel.setPreferredSize(new Dimension(w, h)); // Dostosuj szerokość separatora do swoich potrzeb
        return separatorPanel;
    }

    private void returnSelectedDates() {
        if (endDayComboBox.getSelectedItem() != null && startDayComboBox.getSelectedItem() != null) {
            int startYear = (int) startYearComboBox.getSelectedItem();
            int startMonth = (int) startMonthComboBox.getSelectedItem();
            int startDay = (int) startDayComboBox.getSelectedItem();

            int endYear = (int) endYearComboBox.getSelectedItem();
            int endMonth = (int) endMonthComboBox.getSelectedItem();
            int endDay = (int) endDayComboBox.getSelectedItem();

            this.startDate = LocalDate.of(startYear, startMonth, startDay);
            this.endDate = LocalDate.of(endYear, endMonth, endDay);
        }
    }

    public void addReservationCostPanel(JPanel panel) {
        costLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
        costLabel.setFont(new Font(costLabel.getFont().getName(), Font.PLAIN, 24));
        panel.add(costLabel);
    }
    public void updateCost() {
        returnSelectedDates();
        if (startDate != null && endDate != null && (startDate.isBefore(endDate) || startDate.isEqual(endDate))) {
            long numberOfDays = calculateDaysBetween();
            float price = new OfferDAO().findById(offerId).getPrice();
            double totalCost = 0;
            if (discount != null && discount.getValueType() == 0){
                totalCost = numberOfDays * (price - discountValue);
            }
            else {
                totalCost = numberOfDays * (price - price * discountValue / 100);
            }
            this.costLabel.setText(String.format("Cost: %.2f zł", totalCost));
        } else {
            this.costLabel.setText("Cost: N/A");
        }
    }

    public long calculateDaysBetween() {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return daysBetween + 1;
    }

    public LocalDate getStartDate(){
        returnSelectedDates();
        return startDate;
    }

    public LocalDate getEndDate(){
        returnSelectedDates();
        return endDate;
    }

}
