package gui;

import model.Vehicle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Dark-styled popup listing the most loss-making vehicles after a simulation run.
 */
public class LossMakingVehiclesWindow extends JFrame {

    public LossMakingVehiclesWindow(List<Vehicle> vehicles) {
        AppTheme.apply();
        setTitle("Loss-Making Vehicles Report");
        setSize(680, 460);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(AppTheme.BG_DEEP);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BG_DEEP);

        // Header strip
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(AppTheme.BG_HEADER);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(AppTheme.ACCENT_AMBER);
                g.fillRect(0, getHeight() - 1, getWidth(), 1);
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(0, 42));
        header.setBorder(new EmptyBorder(0, 16, 0, 16));

        JLabel title = new JLabel("⚠   MOST LOSS-MAKING VEHICLES");
        title.setFont(AppTheme.FONT_HEADING);
        title.setForeground(AppTheme.ACCENT_AMBER);
        header.add(title, BorderLayout.WEST);

        // Content
        JTextArea area = AppTheme.logArea();
        area.setFont(AppTheme.FONT_MONO_MD);
        StringBuilder sb = new StringBuilder();
        if (vehicles != null && !vehicles.isEmpty()) {
            vehicles.forEach(v -> sb.append(v).append("\n──────────────────────\n"));
        } else {
            sb.append("No data available.");
        }
        area.setText(sb.toString());

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(AppTheme.BG_DEEP);
        content.setBorder(new EmptyBorder(12, 12, 8, 12));
        content.add(AppTheme.scrollPane(area), BorderLayout.CENTER);

        // Footer
        GlowButton closeBtn = new GlowButton("CLOSE");
        closeBtn.addActionListener(e -> dispose());
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        footer.setBackground(AppTheme.BG_HEADER);
        footer.add(closeBtn);

        root.add(header,  BorderLayout.NORTH);
        root.add(content, BorderLayout.CENTER);
        root.add(footer,  BorderLayout.SOUTH);
        setContentPane(root);
        setLocationRelativeTo(null);
    }
}
