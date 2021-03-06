package Steganography;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.plaf.*;
import javax.swing.border.*;

public class Steganograph extends JFrame implements ActionListener {
	private JWindow splashScreen = null;
	private JLabel splashLabel = null;
	private EmbedMessage embedmsg;
	private EmbedFile embedfile;
	private RetriveMessage retrivemsg;
	private RetriveFile retrivefile;
	public Sender send;
	private Login log;
	private Help hlp;
	private JMenuBar menu;
	private JMenu file, win, help;
	private JMenuItem helpItem1, helpItem2;
	public JMenuItem fileItem[] = new JMenuItem[3],
			winItem[] = new JMenuItem[5];
	public JDesktopPane desk;
	private JInternalFrame jif;
	private JToolBar toolBar;
	private Image icon;
	public JButton toolbutton[] = new JButton[9];
	public boolean win1_live, win2_live, win3_live, win4_live, win5_live,
			log_live, help_live;
	int width = 800, height = 570;

	public Steganograph(String str) {
		super(str);
		createSplashScreen();
		showSplashScreen();
		icon = Toolkit.getDefaultToolkit().getImage(
				"src/Steganography/resource/s3.gif");
		setIconImage(icon);
		desk = new JDesktopPane();
		menu = new JMenuBar();
		toolBar = new JToolBar("Steg", JToolBar.VERTICAL);
		helpItem1 = new JMenuItem("About Steganogrpahy");
		helpItem2 = new JMenuItem("Help  ");
		fileItem[0] = new JMenuItem("Login");
		fileItem[1] = new JMenuItem("Save msg");
		fileItem[2] = new JMenuItem("Exit ");
		winItem[0] = new JMenuItem("Embed Message");
		winItem[1] = new JMenuItem("Embed File");
		winItem[2] = new JMenuItem("Retrive Message");
		winItem[3] = new JMenuItem("Retrive File");
		winItem[4] = new JMenuItem("Send File");
		win1_live = false;
		win2_live = false;
		win3_live = false;
		win4_live = false;
		win5_live = false;
		log_live = false;
		help_live = false;
		addToolButtons();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar, BorderLayout.WEST);
		getContentPane().add(desk, BorderLayout.CENTER);
		menu.add(createFileMenu());
		menu.add(createWindowMenu());
		menu.add(createHelpMenu());
		setJMenuBar(menu);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width - width) / 2, (d.height - height) / 2);
		setSize(width, height);
		for (int i = 0; i < 5; i++) {
			winItem[i].setEnabled(false);
			winItem[i].addActionListener(this);
			toolbutton[i].setEnabled(false);
			toolbutton[i].addActionListener(this);
			if (i >= 1)
				toolbutton[i + 4].addActionListener(this);
			if (i < 3)
				fileItem[i].addActionListener(this);
		}
		fileItem[1].setEnabled(false);
		helpItem1.addActionListener(this);
		helpItem2.addActionListener(this);
		log = new Login(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showInternalConfirmDialog(desk,
						"Are you sure you want to exit ?",
						"Close conformation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					if (log.isValid())
						log.reciever.stopServer();
					dispose();
				}
			}
		});

		hideSplash();
	}

	public void addToolButtons() {
		JPanel buttonPanel;
		toolbutton[0] = new JButton(
				"<html><center>Embed<br>Message</center></html>",
				new ImageIcon("src/Steganography/resource/embed.gif"));
		toolbutton[1] = new JButton(
				"<html><center>Embed<br>File</center></html>", new ImageIcon(
						"src/Steganography/resource/embed.gif"));
		toolbutton[2] = new JButton(
				"<html><center>Retrive<br>Message</center></html>",
				new ImageIcon("src/Steganography/resource/retrive.gif"));
		toolbutton[3] = new JButton(
				"<html><center>Retrive<br>File</center></html>", new ImageIcon(
						"src/Steganography/resource/retrive.gif"));
		toolbutton[4] = new JButton(
				"<html><center>Send<br>File</center></html>", new ImageIcon(
						"src/Steganography/resource/send.gif"));
		toolbutton[5] = new JButton("<html><center>Embed<br></center></html>");
		toolbutton[6] = new JButton("<html><center>Retrive</center></html>");
		toolbutton[7] = new JButton("<html><center>Send</center></html>");
		toolbutton[8] = new JButton("<html><center>About ..</center></html>");
		JLabel start = new JLabel("     FORMS  ");
		start.setForeground(Color.BLUE);
		JLabel oper = new JLabel(
				"<html><center><B>OPERATIONS</B></center></html>");
		oper.setFont(new Font("Dialog", Font.BOLD, 12));
		JLabel separator = new JLabel("<html><center>________");
		separator.setFont(new Font("Dialog", Font.BOLD, 16));
		separator.setForeground(new Color(99, 99, 156));
		oper.setForeground(Color.BLUE);
		toolBar.add(start);
		toolBar.addSeparator();
		for (int i = 0; i < 9; i++) {
			toolbutton[i].setBorder(BorderFactory.createLineBorder(new Color(
					99, 99, 156)));
			toolbutton[i].setVerticalTextPosition(AbstractButton.BOTTOM);
			toolbutton[i].setHorizontalTextPosition(AbstractButton.CENTER);
			toolBar.add(toolbutton[i]);
			if (i == 4) {
				toolBar.add(separator);
				toolBar.add(oper);
			}
			if (i > 4)
				toolbutton[i].setFont(new Font("Dialog", Font.CENTER_BASELINE,
						14));
			toolBar.addSeparator();
		}
		toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
	}

	private JMenu createFileMenu() {
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		fileItem[0].setMnemonic(KeyEvent.VK_L);
		fileItem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		fileItem[1].setMnemonic(KeyEvent.VK_S);
		fileItem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		fileItem[2].setMnemonic(KeyEvent.VK_X);
		fileItem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.ALT_MASK));
		file.add(fileItem[0]);
		file.add(fileItem[1]);
		file.addSeparator();
		file.add(fileItem[2]);
		return file;
	}

	private JMenu createWindowMenu() {
		win = new JMenu("Window");
		win.setMnemonic(KeyEvent.VK_W);
		winItem[0].setMnemonic(KeyEvent.VK_F1);
		winItem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
				ActionEvent.CTRL_MASK));
		winItem[1].setMnemonic(KeyEvent.VK_F2);
		winItem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,
				ActionEvent.CTRL_MASK));
		winItem[2].setMnemonic(KeyEvent.VK_F3);
		winItem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,
				ActionEvent.CTRL_MASK));
		winItem[3].setMnemonic(KeyEvent.VK_F4);
		winItem[3].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.CTRL_MASK));
		winItem[4].setMnemonic(KeyEvent.VK_F5);
		winItem[4].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,
				ActionEvent.CTRL_MASK));
		for (int i = 0; i < 5; i++)
			win.add(winItem[i]);
		return win;
	}

	private JMenu createHelpMenu() {
		help = new JMenu("Help");
		helpItem1.setMnemonic(KeyEvent.VK_F2);
		helpItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,
				ActionEvent.SHIFT_MASK));
		helpItem2.setMnemonic(KeyEvent.VK_F1);
		helpItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
				ActionEvent.SHIFT_MASK));
		help.add(helpItem1);
		help.add(helpItem2);
		return help;
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == winItem[0] || ae.getSource() == toolbutton[0]) {
			if (!win1_live) {
				embedmsg = new EmbedMessage(this);
				desk.add(embedmsg, BorderLayout.CENTER);
				try {
					embedmsg.setVisible(true);
					embedmsg.setSelected(true);
				} catch (Exception e) {
				}
			} else {
				try {
					embedmsg.setIcon(false);
					embedmsg.moveToFront();
					embedmsg.setSelected(true);
				} catch (Exception e) {
				}
			}
		} else if (ae.getSource() == winItem[1]
				|| ae.getSource() == toolbutton[1]) {
			if (!win2_live) {
				embedfile = new EmbedFile(this);
				desk.add(embedfile);
				try {
					embedfile.setVisible(true);
					embedfile.setSelected(true);
				} catch (Exception e) {
				}
			} else {
				try {
					embedfile.setIcon(false);
					embedfile.moveToFront();
					embedfile.setSelected(true);
				} catch (Exception e) {
				}
			}
		} else if (ae.getSource() == winItem[2]
				|| ae.getSource() == toolbutton[2]) {
			if (!win3_live) {
				retrivemsg = new RetriveMessage(this);
				desk.add(retrivemsg);
				try {
					retrivemsg.setVisible(true);
					retrivemsg.setSelected(true);
				} catch (Exception e) {
				}
			} else {
				try {
					retrivemsg.setIcon(false);
					retrivemsg.moveToFront();
					retrivemsg.setSelected(true);
				} catch (Exception e) {
				}
			}
		} else if (ae.getSource() == winItem[3]
				|| ae.getSource() == toolbutton[3]) {
			if (!win4_live) {
				retrivefile = new RetriveFile(this);
				desk.add(retrivefile);
				try {
					retrivefile.setVisible(true);
					retrivefile.setSelected(true);
				} catch (Exception e) {
				}
			} else {
				try {
					retrivefile.setIcon(false);
					retrivefile.moveToFront();
					retrivefile.setSelected(true);
				} catch (Exception e) {
				}
			}
		} else if (ae.getSource() == winItem[4]
				|| ae.getSource() == toolbutton[4]) {
			if (!win5_live) {
				send = new Sender((File) null, this);
				desk.add(send);
				try {
					send.setVisible(true);
					send.setSelected(true);
				} catch (Exception e) {
				}
			} else {
				try {
					send.setIcon(false);
					send.moveToFront();
					send.setSelected(true);
				} catch (Exception e) {
				}
			}
		} else if (ae.getSource() == fileItem[0]) {
			fileItem[0].setEnabled(false);
			if (!log_live)
				log = new Login(this);
			desk.add(log);
			try {
				log.setVisible(true);
				log.setSelected(true);
			} catch (Exception e) {
			}
		} else if (ae.getSource() == fileItem[1]) {
			retrivemsg.actionPerformed(ae);
		} else if (ae.getSource() == fileItem[2]) {
			int result = JOptionPane.showInternalConfirmDialog(desk,
					"Are you sure you want to exit ?", "Close conformation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				if (log.isValid())
					log.reciever.stopServer();
				this.dispose();
			}
		} else if (ae.getSource() == helpItem1
				|| ae.getSource() == toolbutton[8]) {
			about();
		} else if (ae.getSource() == helpItem2) {
			if (!help_live) {
				hlp = new Help(this);
				desk.add(hlp);
				try {
					hlp.setVisible(true);
					hlp.setSelected(true);
				} catch (Exception e) {
				}
			} else {
				try {
					hlp.setIcon(false);
					hlp.moveToFront();
				} catch (Exception e) {
				}
			}
		} else if (ae.getSource() == toolbutton[5]) {
			if (win1_live) {
				if (!embedmsg.isIcon() && embedmsg.isSelected())
					embedmsg.actionPerformed(ae);
			}
			if (win2_live) {
				if (!embedfile.isIcon() && embedfile.isSelected())
					embedfile.actionPerformed(ae);
			}
		} else if (ae.getSource() == toolbutton[6]) {
			if (win3_live) {
				if (!retrivemsg.isIcon() && retrivemsg.isSelected())
					retrivemsg.actionPerformed(ae);
			}
			if (win4_live) {
				if (!retrivefile.isIcon() && retrivefile.isSelected())
					retrivefile.actionPerformed(ae);
			}
		} else if (ae.getSource() == toolbutton[7]) {
			if (win5_live) {
				if (!send.isIcon() && send.isSelected())
					send.actionPerformed(ae);
			}
			if (win1_live) {
				if (!embedmsg.isIcon() && embedmsg.isSelected())
					embedmsg.actionPerformed(ae);
			}
			if (win2_live) {
				if (!embedfile.isIcon() && embedfile.isSelected())
					embedfile.actionPerformed(ae);
			}
		}
	}

	public static void main(String args[]) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		Steganograph steganoFrame = new Steganograph("CryptStego");
		steganoFrame.setVisible(true);
	}

	public void about() {
		JLabel title = new JLabel("CryptStego Implementation ");
		title.setFont(new Font("Times-Roman", Font.BOLD, 24));
		title.setForeground(Color.RED);
		JLabel name1 = new JLabel("Nilesh Mishra", new ImageIcon(
				"src/Steganography/resource/s3.gif"), JLabel.CENTER);
		name1.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		JLabel name2 = new JLabel("Santosh Chaudhary", new ImageIcon(
				"src/Steganography/resource/s3.gif"), JLabel.CENTER);
		name2.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		JLabel name3 = new JLabel("Harshita Panchal", new ImageIcon(
				"src/Steganography/resource/s3.gif"), JLabel.CENTER);
		name3.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		JLabel name4 = new JLabel("Sara Shaikh", new ImageIcon(
		"src/Steganography/resource/s3.gif"), JLabel.CENTER);
name4.setFont(new Font("Monotype Corsiva", Font.BOLD, 20));
		JLabel image = new JLabel(new ImageIcon(
				"src/Steganography/resource/s2.gif"));
		Object[] ob = new Object[12];
		ob[0] = (Object) title;
		ob[1] = new JSeparator();
		ob[2] = "\nThis application \"CryptStego\" deals with the data security.\n"
				+ "This project was created for final year Diploma TYCM sem VI.\n\n";
		ob[3] = new JSeparator();
		ob[4] = new JLabel(" ");
		ob[5] = new JLabel(" ");
		ob[6] = null;
		ob[7] = new String("Developers :");
		ob[8] = (Object) name1;
		ob[9] = (Object) name2;
		ob[10] = (Object) name3;
		ob[11] = (Object) name4;
		String[] but = new String[1];
		but[0] = "OK";
		int result = JOptionPane.showInternalOptionDialog(desk, ob,
				"About CryptStego", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
						"resource/s2.gif"), but, but[0]);
	}

	public void createSplashScreen() {
		splashLabel = new JLabel(new ImageIcon(
				"src/Steganography/resource/hacker.jpg",
				"Splash.accessible_description"));
		splashScreen = new JWindow();
		splashScreen.getContentPane().add(splashLabel);
		splashScreen.pack();
		Rectangle rect = splashScreen.getBounds();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		splashScreen.setLocation((int) (d.width - rect.getWidth()) / 2,
				(int) (d.height - rect.getHeight()) / 2);
	}

	public void showSplashScreen() {
		splashScreen.setVisible(true);
		// try {
		// splashScreen.wait(3000);
		// } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		// splashScreen.dispose();
		// }
	}

	public void hideSplash() {
		splashScreen.dispose();
	}
}
