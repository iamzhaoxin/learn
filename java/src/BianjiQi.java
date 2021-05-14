import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.event.*;

public class BianjiQi extends JFrame
{
    Container c = new Container();
    JMenuBar jmb = new JMenuBar();
    JMenu file = new JMenu("文件");
    JMenuItem open = new JMenuItem("打开");
    JMenuItem save = new JMenuItem("保存");
    JMenuItem exit = new JMenuItem("编辑");
    JMenuItem copy = new JMenuItem("复制");
    JMenuItem cut = new JMenuItem("剪贴");
    JMenuItem paste = new JMenuItem("转换");
    JMenuItem delete = new JMenuItem("删除");
    JTextPane ta = new JTextPane();
    JFileChooser chooser = new JFileChooser();
    FileInputStream filestream = null;
    String selected = new String();
    int dot, mark;
    public BianjiQi()
    {
        chooser.setSize(400, 350);
        chooser.setDialogTitle("保存");
        chooser.setVisible(true);
        Font f = new Font("TimesRoman", Font.PLAIN, 16);
        c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(ta, "Center");
        this.setJMenuBar(jmb);
        jmb.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        jmb.add(file);
        file.add(open);
        file.add(save);
        file.add(exit);
        open.addActionListener(new ListenActionForJfilechooser());
        save.addActionListener(new ListenActionForJfilechooser());
        exit.addActionListener(new exitListener());
        copy.addActionListener(new copyListener());
        cut.addActionListener(new cutListener());
        paste.addActionListener(new pasteListener());
        delete.addActionListener(new deleteListener());
        ta.addCaretListener(new taListener());
        ta.setFont(f);
    }
    class boldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Font f = new Font("TimesRoman", Font.BOLD, 16);
            ta.setFont(f);
        }
    }
    class italicListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Font f = new Font("TimesRoman", Font.ITALIC, 16);
            ta.setFont(f);
        }
    }
    class deleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Font f = new Font("TimesRoman", Font.PLAIN, 16);
            ta.setText(null);
            ta.setFont(f);
        }
    }
    class cutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s1 = new String();
            selected = selectedString();
            s1 = ta.getText();
            int length = s1.length();
            if (dot < mark) {
                ta.setText(s1.substring(0, dot) + s1.substring(mark, length));
            } else {
                ta.setText(s1.substring(0, mark) + s1.substring(dot, length));
            }
        }
    }
    class pasteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ta.paste();
        }
    }
    class taListener implements CaretListener {
        public void caretUpdate(CaretEvent e) {
            dot = e.getDot(); // 取得光标开始位置
            mark = e.getMark();// 取得光标结束位置
        }
    }
    class copyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selected = selectedString();
        }
    }
    class exitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    class ListenActionForJfilechooser implements ActionListener {
        int result;
        File file;
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == open) {
                int state = chooser.showOpenDialog(null);
                file = chooser.getSelectedFile();
                if (file != null && state == JFileChooser.APPROVE_OPTION) {
                    try {
                        filestream = new FileInputStream(file);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    try {
                        ta.read(filestream, this);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (e.getSource() == save) {
                result = chooser.showSaveDialog(ta);
                file = null;
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                } else if (result == JFileChooser.CANCEL_OPTION) {
                }
                FileOutputStream fileOutStream = null;
                if (file != null) {
                    try {
                        fileOutStream = new FileOutputStream(file);
                    } catch (FileNotFoundException fe) {
                        return;
                    }
                    String content = ta.getText();
                    try {
                        fileOutStream.write(content.getBytes());
                    } catch (IOException ioe) {
                    } finally {
                        try {
                            if (fileOutStream != null)
                                fileOutStream.close();
                        } catch (IOException ioe2) {
                        }
                    }
                }
            }
        }
    }
    String selectedString() {
        String s = new String();
        s = ta.getText();
        if (dot < mark) {
            s = s.substring(dot, mark);
        } else {
            s = s.substring(mark, dot);
        }
        return s;
    }
    public static void main(String[] args)
    {
        BianjiQi b = new BianjiQi();
        b.setTitle("FileChooserTest");
        b.setSize(500, 400);
        b.setVisible(true);
    }
}
