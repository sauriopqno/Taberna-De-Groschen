package vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;

import consultas.UsuarioSQL;
import modelo.Usuario;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;

    public static void cargarFuenteGlobal() {
        try {
            InputStream in = Login.class.getResourceAsStream("/fuente/PixAntiqua.ttf");
            Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, in);
            Font fuenteMedieval = fuenteBase.deriveFont(Font.PLAIN, 13f);
            javax.swing.plaf.FontUIResource fontUI = new javax.swing.plaf.FontUIResource(fuenteMedieval);
            java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof javax.swing.plaf.FontUIResource) {
                    UIManager.put(key, fontUI);
                }
            }
        } catch (Exception e) {
            System.out.println("Error cargando la fuente medieval: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error desactivando el tema del sistema: " + e.getMessage());
        }
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        cargarFuenteGlobal();
        audio.getInstancia().iniciarMusicaTaberna();
        
        setTitle("Login - Taberna del Groschen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 617, 450);
        setResizable(false);
        setLocationRelativeTo(null);
    
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL fondoURL = getClass().getResource("/imagenes/Fondo_taberna_fuera.png");
                if (fondoURL != null) {
                    Image imagen = new javax.swing.ImageIcon(fondoURL).getImage();
                    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel panelCard = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(25, 15, 10, 215));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelCard.setOpaque(false);
        panelCard.setBorder(BorderFactory.createLineBorder(new Color(180, 140, 50), 1));
        panelCard.setBounds(128, 35, 360, 345);
        panelCard.setLayout(null);
        contentPane.add(panelCard);
        
        //COMPONENTES INTERNOS DEL FORMULARIO
        
        JLabel user = new JLabel("Nombre de Usuario:");
        user.setForeground(Color.WHITE);
        user.setBounds(40, 25, 280, 20);
        panelCard.add(user);
        
        textField = new JTextField();
        textField.setBounds(40, 48, 280, 30);
        panelCard.add(textField);
        textField.setColumns(10);
        
        JLabel pass = new JLabel("Password:");
        pass.setForeground(Color.WHITE);
        pass.setBounds(40, 93, 280, 20);
        panelCard.add(pass);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(40, 116, 280, 30);
        passwordField.setEchoChar('*');
        panelCard.add(passwordField);
        
        JButton login = new JButton("Ingresar a la Taberna");
        botones.estilizarBoton(login);
        login.setBounds(40, 165, 280, 35);
        panelCard.add(login);
        
        JLabel lblNewLabel = new JLabel("¿No tienes cuenta? Crea una nueva");
        lblNewLabel.setForeground(new Color(200, 200, 200));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(40, 235, 280, 20);
        panelCard.add(lblNewLabel);
        
        JButton register = new JButton("Registrarse");
        botones.estilizarBoton(register);
        register.setBounds(65, 265, 230, 30);
        panelCard.add(register);
        
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String u = textField.getText();
                String p = new String(passwordField.getPassword());
                
                UsuarioSQL sql = new UsuarioSQL();
                Usuario userLogueado = sql.validarAcceso(u, p);
                
                if (userLogueado != null) {
                    if(userLogueado.getUser().equals("admin")) {
                        CocinaGroschen ventanaCocina = new CocinaGroschen();
                        ventanaCocina.setVisible(true);
                    } else {
                        TabernaDeGroschen principal = new TabernaDeGroschen(userLogueado);
                        principal.setVisible(true);
                    }
                    Login.this.dispose();
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos, viajero.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register ventanaRegistro = new Register();
                ventanaRegistro.setVisible(true);
                Login.this.dispose();
            }
        });
    }
}