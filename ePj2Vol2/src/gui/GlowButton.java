package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * A custom button with a neon-glow border and animated hover/press states.
 * Renders entirely via paintComponent — no L&F delegates involved.
 */
public class GlowButton extends JButton {

    private static final int ARC = 6;

    private boolean hovered  = false;
    private boolean pressed  = false;
    private float   glowAlpha = 0f;

    public GlowButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(AppTheme.FONT_HEADING);
        setForeground(AppTheme.TEXT_PRIMARY);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(10, 28, 10, 28));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e)  { hovered = true;  animateGlow(true);  repaint(); }
            @Override public void mouseExited(MouseEvent e)   { hovered = false; animateGlow(false); repaint(); }
            @Override public void mousePressed(MouseEvent e)  { pressed = true;  repaint(); }
            @Override public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
        });
    }

    private void animateGlow(boolean in) {
        Timer timer = new Timer(16, null);
        timer.addActionListener(e -> {
            glowAlpha += in ? 0.08f : -0.08f;
            glowAlpha  = Math.max(0f, Math.min(1f, glowAlpha));
            repaint();
            if ((in && glowAlpha >= 1f) || (!in && glowAlpha <= 0f)) timer.stop();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        // --- Glow layers (painted largest → smallest) ---
        if (glowAlpha > 0) {
            for (int i = 4; i >= 1; i--) {
                int spread = i * 3;
                g2.setColor(new Color(0x00, 0xD4, 0xFF, (int)(glowAlpha * 18)));
                g2.fillRoundRect(-spread, -spread, w + spread * 2, h + spread * 2, ARC + spread, ARC + spread);
            }
        }

        // --- Body fill ---
        Color bodyColor = pressed
                ? new Color(0x00, 0x8A, 0xAA)
                : hovered
                        ? new Color(0x00, 0x4A, 0x66)
                        : new Color(6, 26, 38);
        g2.setColor(bodyColor);
        g2.fillRoundRect(0, 0, w - 1, h - 1, ARC, ARC);

        // --- Border ---
        g2.setStroke(new BasicStroke(hovered ? 1.5f : 1f));
        g2.setColor(hovered ? AppTheme.ACCENT_CYAN : AppTheme.BORDER_BRIGHT);
        g2.drawRoundRect(0, 0, w - 1, h - 1, ARC, ARC);

        // --- Top highlight stripe ---
        g2.setColor(new Color(0xFF, 0xFF, 0xFF, 14));
        g2.fillRoundRect(2, 2, w - 4, h / 2 - 2, ARC - 1, ARC - 1);

        // --- Label ---
        FontMetrics fm = g2.getFontMetrics(getFont());
        String label  = getText();
        int tx = (w - fm.stringWidth(label)) / 2;
        int ty = (h + fm.getAscent() - fm.getDescent()) / 2;

        // subtle shadow
        g2.setFont(getFont());
        g2.setColor(new Color(0, 0, 0, 120));
        g2.drawString(label, tx + 1, ty + 1);

        g2.setColor(hovered ? AppTheme.ACCENT_CYAN : AppTheme.TEXT_PRIMARY);
        g2.drawString(label, tx, ty);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        return new Dimension(Math.max(d.width, 180), Math.max(d.height, 40));
    }
}
