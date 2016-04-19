package test;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.html.*;

import database.*;
import general.*;

public class DatabaseTEST extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private ADatabaseHANDLE database;
	
	public DatabaseTEST() {
		super("TEST");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.database = new DatabaseHANDLE(Parameters.DATABASE);
		
		this.init();

		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	private void init() {
		Container container = this.getContentPane();
		container.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		container.setPreferredSize(new Dimension(600, 539));

		JPanel PANEL1 = new JPanel();
		this.PANEL1(PANEL1);
		TitledBorder BOARDER1 = BorderFactory.createTitledBorder("insertChatUser(user : ChatUser) : boolean");
		PANEL1.setBorder(BOARDER1);
		container.add(PANEL1);
		
		JPanel PANEL2 = new JPanel();
		this.PANEL2(PANEL2);
		TitledBorder BOARDER2 = BorderFactory.createTitledBorder("insertChatMessage(message : ChatMessage) : boolean");
		PANEL2.setBorder(BOARDER2);
		container.add(PANEL2);	
		
		JPanel PANEL3 = new JPanel();
		this.PANEL3(PANEL3);
		TitledBorder BOARDER3 = BorderFactory.createTitledBorder("updateUnread(userto : ChatUser) : boolean");
		PANEL3.setBorder(BOARDER3);
		container.add(PANEL3);	
		
		JPanel PANEL4 = new JPanel();
		this.PANEL4(PANEL4);
		TitledBorder BOARDER4 = BorderFactory.createTitledBorder("existsChatUser(user : ChatUser) : boolean");
		PANEL4.setBorder(BOARDER4);
		container.add(PANEL4);
		
		JPanel PANEL5 = new JPanel();
		this.PANEL5(PANEL5);
		TitledBorder BOARDER5 = BorderFactory.createTitledBorder("selectChatUsers() : List<ChatUser>");
		PANEL5.setBorder(BOARDER5);
		container.add(PANEL5);
		
		JPanel PANEL6 = new JPanel();
		this.PANEL6(PANEL6);
		TitledBorder BOARDER6 = BorderFactory.createTitledBorder("selectChatHistory(userfrom : ChatUser) : List<ChatHistory>");
		PANEL6.setBorder(BOARDER6);
		container.add(PANEL6);
		
		JPanel PANEL7 = new JPanel();
		this.PANEL7(PANEL7);
		TitledBorder BOARDER7 = BorderFactory.createTitledBorder("selectUnreadChatHistory(userto : ChatUser) : List<ChatHistory>");
		PANEL7.setBorder(BOARDER7);
		container.add(PANEL7);
	}
	
	private void PANEL1(JPanel panel){
		panel.setLayout(new BorderLayout(10, 10));
			
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(10, 0));
		
		JTextField username = new JTextField("username");
		username.addFocusListener(new FocusHandler(username));
		JButton button = new JButton("INSERT");
		button.setPreferredSize(new Dimension(100, 29));
		button.addActionListener(new QUERY1(username));
		
		content.add(BorderLayout.CENTER, username);
		content.add(BorderLayout.EAST, button);
		
		panel.add(new JLabel(""), BorderLayout.NORTH);
		panel.add(new JLabel(""), BorderLayout.EAST);
		panel.add(new JLabel(""), BorderLayout.WEST);
		panel.add(new JLabel(""), BorderLayout.SOUTH);
		panel.add(BorderLayout.CENTER, content);
	}
	
	private class QUERY1 implements ActionListener {
		
		private JTextField username;
		
		public QUERY1(JTextField username) {
			this.username = username;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean result = DatabaseTEST.this.database.insertChatUser(new ChatUser(this.username.getText()));
			
			if(result) {
				this.username.setText("username");
				JOptionPane.showMessageDialog(null, "INSERT ERFOLGREICH", "QUERY1", JOptionPane.PLAIN_MESSAGE);
			} else {
				this.username.setText("username");
				JOptionPane.showMessageDialog(null, "INSERT NICHT ERFOLGREICH (username EXISTIERT VERMUTLICH SCHON!)", "QUERY1", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
	}
	
	private void PANEL2(JPanel panel){
		panel.setLayout(new BorderLayout(10, 10));
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(10, 0));
		
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.LINE_AXIS));
		JTextField userfrom = new JTextField("userfrom");
		userfrom.addFocusListener(new FocusHandler(userfrom));
		center.add(userfrom);
		JTextField userto = new JTextField("userto");
		userto.addFocusListener(new FocusHandler(userto));
		center.add(userto);
		JTextField message = new JTextField("message");
		message.addFocusListener(new FocusHandler(message));
		center.add(message);
		JButton button = new JButton("INSERT");
		button.setPreferredSize(new Dimension(100, 29));
		button.addActionListener(new QUERY2(userfrom, userto, message));
		
		content.add(BorderLayout.CENTER, center);
		content.add(BorderLayout.EAST, button);
			
		panel.add(new JLabel(""), BorderLayout.NORTH);
		panel.add(new JLabel(""), BorderLayout.EAST);
		panel.add(new JLabel(""), BorderLayout.WEST);
		panel.add(new JLabel(""), BorderLayout.SOUTH);
		panel.add(BorderLayout.CENTER, content);
	}
		
	private class QUERY2 implements ActionListener {
			
		private JTextField userfrom;
		private JTextField userto;
		private JTextField message;
		
		public QUERY2(JTextField userfrom, JTextField userto, JTextField message) {
			this.userfrom = userfrom;
			this.userto = userto;
			this.message = message;
		}
			
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean result = DatabaseTEST.this.database.insertChatMessage(new ChatMessage(new ChatUser(this.userfrom.getText()), new ChatUser(this.userto.getText()), this.message.getText(), System.currentTimeMillis()));
			
			if(result) {
				this.userfrom.setText("userfrom");
				this.userto.setText("userto");
				this.message.setText("message");
				JOptionPane.showMessageDialog(null, "INSERT ERFOLGREICH", "QUERY2", JOptionPane.PLAIN_MESSAGE);
			} else {
				this.userfrom.setText("userfrom");
				this.userto.setText("userto");
				this.message.setText("message");
				JOptionPane.showMessageDialog(null, "INSERT NICHT ERFOLGREICH (userfrom ODER userto EXISTIERT VERMUTLICH NICHT!)", "QUERY2", JOptionPane.PLAIN_MESSAGE);
			}
		}
			
	}
	
	private void PANEL3(JPanel panel){
		panel.setLayout(new BorderLayout(10, 10));
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(10, 0));
		JTextField userto = new JTextField("userto");
		userto.addFocusListener(new FocusHandler(userto));
		JButton button = new JButton("UPDATE");
		button.setPreferredSize(new Dimension(100, 29));
		button.addActionListener(new QUERY3(userto));
		
		content.add(BorderLayout.CENTER, userto);
		content.add(BorderLayout.EAST, button);
		
		panel.add(new JLabel(""), BorderLayout.NORTH);
		panel.add(new JLabel(""), BorderLayout.EAST);
		panel.add(new JLabel(""), BorderLayout.WEST);
		panel.add(new JLabel(""), BorderLayout.SOUTH);
		panel.add(BorderLayout.CENTER, content);
	}
	
	private class QUERY3 implements ActionListener {
		
		private JTextField userto;
		
		public QUERY3(JTextField userto) {
			this.userto = userto;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean result = DatabaseTEST.this.database.updateUnreadChatMessages(new ChatUser(this.userto.getText()));
			
			if(result) {
				this.userto.setText("userto");
				JOptionPane.showMessageDialog(null, "UPDATE ERFOLGREICH", "QUERY3", JOptionPane.PLAIN_MESSAGE);
			} else {
				this.userto.setText("userto");
				JOptionPane.showMessageDialog(null, "UPDATE NICHT ERFOLGREICH (userto EXISTIERT VERMUTLICH NICHT!)", "QUERY3", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
	}
	
	private void PANEL4(JPanel panel){
		panel.setLayout(new BorderLayout(10, 10));
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(10, 0));
		
		JTextField username = new JTextField("username");
		username.addFocusListener(new FocusHandler(username));
		JButton button = new JButton("SELECT");
		button.setPreferredSize(new Dimension(100, 29));
		button.addActionListener(new QUERY4(username));
		
		content.add(BorderLayout.CENTER, username);
		content.add(BorderLayout.EAST, button);
		
		panel.add(new JLabel(""), BorderLayout.NORTH);
		panel.add(new JLabel(""), BorderLayout.EAST);
		panel.add(new JLabel(""), BorderLayout.WEST);
		panel.add(new JLabel(""), BorderLayout.SOUTH);
		panel.add(BorderLayout.CENTER, content);
	}
	
	private class QUERY4 implements ActionListener {
		
		private JTextField username;
		
		public QUERY4(JTextField username) {
			this.username = username;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean result = DatabaseTEST.this.database.existsChatUser(new ChatUser(this.username.getText()));
			
			if(result) {
				this.username.setText("username");
				JOptionPane.showMessageDialog(null, "BENUTZER EXISTIERT", "QUERY4", JOptionPane.PLAIN_MESSAGE);
			} else {
				this.username.setText("username");
				JOptionPane.showMessageDialog(null, "BENUTZER EXISTIERT NICHT", "QUERY4", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
	}
	
	private void PANEL5(JPanel panel){
		panel.setLayout(new BorderLayout(10, 10));
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(10, 0));

		JButton button = new JButton("SELECT");
		button.setPreferredSize(new Dimension(100, 29));
		button.addActionListener(new QUERY5());
		
		content.add(BorderLayout.EAST, button);
		
		panel.add(new JLabel(""), BorderLayout.NORTH);
		panel.add(new JLabel(""), BorderLayout.EAST);
		panel.add(new JLabel(""), BorderLayout.WEST);
		panel.add(new JLabel(""), BorderLayout.SOUTH);
		panel.add(BorderLayout.CENTER, content);
	}
	
	private class QUERY5 implements ActionListener {
		
		public QUERY5() {}

		@Override
		public void actionPerformed(ActionEvent e) {
			List<ChatUser> users = DatabaseTEST.this.database.selectChatUsers();
			Collections.sort(users);
			
			if(users.isEmpty()) {
				JOptionPane.showMessageDialog(null, "BENUTZERLISTE LEER", "QUERY5", JOptionPane.PLAIN_MESSAGE);
			} else {
				StringBuffer buffer = new StringBuffer();
				
				buffer.append("BENUTZERLISTE: ");
				buffer.append("<br>");
				
				buffer.append("<p>");
				for(ChatUser u : users) {
					buffer.append(u);
					buffer.append("<br>");
				}
				buffer.append("</p>");
				
				buffer.append("</html>");
				
				JEditorPane content = new JEditorPane();
				content.setBackground(UIManager.getColor("Panel.background"));
				content.setEditable(false);
				content.setContentType("text/html");
		        Font font = UIManager.getFont("Label.font");
		        String rule = "body { font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt; margin: 10px 10px;}";
		        ((HTMLDocument) content.getDocument()).getStyleSheet().addRule(rule);
		        
		        content.setText(buffer.toString());
		        
		        JScrollPane pane = new JScrollPane(content);
		        pane.setPreferredSize(new Dimension(500, 350));
		        pane.setBorder(null);
				
				JOptionPane.showMessageDialog(null, pane, "QUERY5", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
	}
	
	private void PANEL6(JPanel panel){
		panel.setLayout(new BorderLayout(10, 10));
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(10, 0));
		
		JTextField userfrom = new JTextField("userfrom");
		userfrom.addFocusListener(new FocusHandler(userfrom));
		JButton button = new JButton("SELECT");
		button.setPreferredSize(new Dimension(100, 29));
		button.addActionListener(new QUERY6(userfrom));
		
		content.add(BorderLayout.CENTER, userfrom);
		content.add(BorderLayout.EAST, button);
		
		panel.add(new JLabel(""), BorderLayout.NORTH);
		panel.add(new JLabel(""), BorderLayout.EAST);
		panel.add(new JLabel(""), BorderLayout.WEST);
		panel.add(new JLabel(""), BorderLayout.SOUTH);
		panel.add(BorderLayout.CENTER, content);
	}
	
	private class QUERY6 implements ActionListener {
		
		private JTextField userfrom;
		
		public QUERY6(JTextField userfrom) {
			this.userfrom = userfrom;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			List<ChatHistory> histories = DatabaseTEST.this.database.selectChatHistory(new ChatUser(this.userfrom.getText()));
			
			if(histories.isEmpty()) {
				this.userfrom.setText("userfrom");
				JOptionPane.showMessageDialog(null, "CHATHISTORY LEER", "QUERY6", JOptionPane.PLAIN_MESSAGE);
			} else {
				StringBuffer buffer = new StringBuffer();
				
				buffer.append("CHATHISTORY VON <b>" + this.userfrom.getText() + "</b> ...");
				buffer.append("<br>");
				
				for(ChatHistory history : histories) {
					buffer.append("<p>");
					buffer.append("... MIT <b>" + history.getOther()  +"</b><br>");
					for(ChatMessage message : history.getMessages()) {
						buffer.append(message.getFrom() + " -> " + message.getTo() + ": " + message.getMessage() + "<br>");
					}
					buffer.append("</p>");
				}
				
				buffer.append("</html>");
				
				JEditorPane content = new JEditorPane();
				content.setBackground(UIManager.getColor("Panel.background"));
				content.setEditable(false);
				content.setContentType("text/html");
		        Font font = UIManager.getFont("Label.font");
		        String rule = "body { font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt; margin: 10px 10px;}";
		        ((HTMLDocument) content.getDocument()).getStyleSheet().addRule(rule);
		        
		        content.setText(buffer.toString());
		        
		        JScrollPane pane = new JScrollPane(content);
		        pane.setPreferredSize(new Dimension(500, 350));
		        pane.setBorder(null);
				
		        this.userfrom.setText("userfrom");
				JOptionPane.showMessageDialog(null, pane, "QUERY6", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
	}
	
	private void PANEL7(JPanel panel){
		panel.setLayout(new BorderLayout(10, 10));
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(10, 0));
		
		JTextField userto = new JTextField("userto");
		userto.addFocusListener(new FocusHandler(userto));
		JButton button = new JButton("SELECT");
		button.setPreferredSize(new Dimension(100, 29));
		button.addActionListener(new QUERY7(userto));
		
		content.add(BorderLayout.CENTER, userto);
		content.add(BorderLayout.EAST, button);
		
		panel.add(new JLabel(""), BorderLayout.NORTH);
		panel.add(new JLabel(""), BorderLayout.EAST);
		panel.add(new JLabel(""), BorderLayout.WEST);
		panel.add(new JLabel(""), BorderLayout.SOUTH);
		panel.add(BorderLayout.CENTER, content);
	}
	
	private class QUERY7 implements ActionListener {
		
		private JTextField userto;
		
		public QUERY7(JTextField userto) {
			this.userto = userto;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			List<ChatHistory> histories = DatabaseTEST.this.database.selectUnreadChatHistory(new ChatUser(this.userto.getText()));
			
			if(histories.isEmpty()) {
				this.userto.setText("userto");
				JOptionPane.showMessageDialog(null, "CHATHISTORY (UNREAD) LEER", "QUERY7", JOptionPane.PLAIN_MESSAGE);
			} else {
				StringBuffer buffer = new StringBuffer();
				
				buffer.append("CHATHISTORY (UNREAD) VON <b>" + this.userto.getText() + "</b> ...");
				buffer.append("<br>");
				
				for(ChatHistory history : histories) {
					buffer.append("<p>");
					buffer.append("... MIT <b>" + history.getOther()  +"</b><br>");
					for(ChatMessage message : history.getMessages()) {
						buffer.append(message.getFrom() + " -> " + message.getTo() + ": " + message.getMessage() + "<br>");
					}
					buffer.append("</p>");
				}
				
				buffer.append("</html>");
				
				JEditorPane content = new JEditorPane();
				content.setBackground(UIManager.getColor("Panel.background"));
				content.setEditable(false);
				content.setContentType("text/html");
		        Font font = UIManager.getFont("Label.font");
		        String rule = "body { font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt; margin: 10px 10px;}";
		        ((HTMLDocument) content.getDocument()).getStyleSheet().addRule(rule);
		        
		        content.setText(buffer.toString());
		        
		        JScrollPane pane = new JScrollPane(content);
		        pane.setPreferredSize(new Dimension(500, 350));
		        pane.setBorder(null);
				
		        this.userto.setText("userto");
				JOptionPane.showMessageDialog(null, pane, "QUERY7", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
	}
	
	private class FocusHandler implements FocusListener {
		
		private String initial;
		private JTextField textfield;
		
		public FocusHandler(JTextField textfield) {
			this.textfield = textfield;
			this.initial = this.textfield.getText();
		}

		@Override
		public void focusGained(FocusEvent e) {
			if(this.textfield.getText().equals(this.initial)) {
				this.textfield.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(this.textfield.getText().equals("")) {
				this.textfield.setText(this.initial);
			}
		}
		
	}
	
	public static void main(String[] args) {
		DatabaseTEST test = new DatabaseTEST();
		test.setVisible(true);
	}
	
}
