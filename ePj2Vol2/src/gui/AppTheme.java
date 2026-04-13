package gui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Central design system for the JavaCity dashboard.
 * Aesthetic: dark tactical HUD — mission control for a smart city.
 */
public final class AppTheme {

    private AppTheme() {}

    // ── Colours ──────────────────────────────────────────────────────────────
    public static final Color BG_DEEP         = new Color(0x07, 0x0D, 0x18);
    public static final Color BG_PANEL        = new Color(0x0D, 0x17, 0x26);
    public static final Color BG_CARD         = new Color(0x0A, 0x14, 0x22);
    public static final Color BG_HEADER       = new Color(0x0A, 0x12, 0x1F);
    public static final Color BG_PANEL_TITLE  = new Color(0x00, 0x35, 0x50);

    public static final Color ACCENT_CYAN     = new Color(0x00, 0xD4, 0xFF);
    public static final Color ACCENT_CYAN_DIM = new Color(0x00, 0x7A, 0x99);
    public static final Color ACCENT_AMBER    = new Color(0xFF, 0xB3, 0x00);

    public static final Color BORDER_DIM      = new Color(0x1A, 0x35, 0x52);
    public static final Color BORDER_BRIGHT   = new Color(0x00, 0x6E, 0x8A);

    public static final Color TEXT_PRIMARY    = new Color(0xDC, 0xE8, 0xF5);
    public static final Color TEXT_SECONDARY  = new Color(0x94, 0xA3, 0xB8);

    // Vehicle colours
    public static final Color COLOR_CAR       = new Color(0xFF, 0x45, 0x2B);
    public static final Color COLOR_BIKE      = new Color(0xFF, 0xCC, 0x00);
    public static final Color COLOR_SCOOTER   = new Color(0x00, 0xFF, 0x94);

    // Map zone colours
    public static final Color MAP_DOWNTOWN    = new Color(0x0A, 0x1A, 0x30);
    public static final Color MAP_SUBURB      = new Color(0x07, 0x10, 0x1C);
    public static final Color MAP_GRID        = new Color(0x14, 0x2E, 0x48);

    // ── Fonts ─────────────────────────────────────────────────────────────────
    public static final Font FONT_MONO_SM  = new Font("Courier New", Font.PLAIN, 11);
    public static final Font FONT_MONO_MD  = new Font("Courier New", Font.PLAIN, 13);
    public static final Font FONT_LABEL    = new Font("Courier New", Font.BOLD,  11);
    public static final Font FONT_TITLE    = new Font("Courier New", Font.BOLD,  13);
    public static final Font FONT_HEADING  = new Font("Courier New", Font.BOLD,  15);

    // ── UIManager defaults ────────────────────────────────────────────────────
    public static void apply() {
        UIManager.put("Panel.background",          BG_PANEL);
        UIManager.put("ScrollPane.background",     BG_PANEL);
        UIManager.put("Viewport.background",       BG_CARD);
        UIManager.put("TextArea.background",       BG_CARD);
        UIManager.put("TextArea.foreground",       TEXT_PRIMARY);
        UIManager.put("TextArea.caretForeground",  ACCENT_CYAN);
        UIManager.put("TextArea.selectionColor",   ACCENT_CYAN_DIM);
        UIManager.put("TextArea.font",             FONT_MONO_SM);
        UIManager.put("ScrollBar.background",      BG_DEEP);
        UIManager.put("ScrollBar.thumb",           BORDER_DIM);
        UIManager.put("ScrollBar.thumbDarkShadow", BG_DEEP);
        UIManager.put("ScrollBar.thumbHighlight",  BORDER_BRIGHT);
        UIManager.put("ScrollBar.thumbShadow",     BG_DEEP);
        UIManager.put("ScrollBar.track",           BG_DEEP);
        UIManager.put("ScrollBar.width",           6);
    }

    // ── Factory helpers ───────────────────────────────────────────────────────

    /** Styled scroll pane — no visible border, dark viewport. */
    public static JScrollPane scrollPane(Component view) {
        JScrollPane sp = new JScrollPane(view);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setBackground(BG_CARD);
        sp.getViewport().setBackground(BG_CARD);
        sp.getVerticalScrollBar().setBackground(BG_DEEP);
        sp.getHorizontalScrollBar().setBackground(BG_DEEP);
        return sp;
    }

    /** Read-only monospace log text area. */
    public static JTextArea logArea() {
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setBackground(BG_CARD);
        ta.setForeground(TEXT_PRIMARY);
        ta.setFont(FONT_MONO_SM);
        ta.setCaretColor(ACCENT_CYAN);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setMargin(new Insets(6, 8, 6, 8));
        return ta;
    }

    /**
     * Wraps {@code content} in a titled HUD panel.
     *
     * The title is a genuine {@link JLabel} inside a real header {@link JPanel},
     * so it is NEVER clipped by the parent layout manager.
     *
     * Structure:
     * ┌──────────────────────────────────┐
     * │■ TITLE                           │   ← header JPanel (26px tall)
     * ├──────────────────────────────────┤   ← 1px ACCENT_CYAN_DIM separator
     * │                                  │
     * │         content                  │   ← CENTER
     * │                                  │
     * └──────────────────────────────────┘
     */
    public static JPanel hudPanel(String title, Component content) {
        // ── Outer container ──────────────────────────────────────────────────
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(BG_CARD);
        panel.setBorder(BorderFactory.createLineBorder(BORDER_DIM, 1));

        // ── Header strip ─────────────────────────────────────────────────────
        // Fully opaque, 34px tall — bright left accent stripe, white text.
        JPanel header = new JPanel(new BorderLayout(0, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0x00, 0x28, 0x40));
                g2.fillRect(0, 0, getWidth(), getHeight());
                // 4px cyan accent stripe on the left
                g2.setColor(ACCENT_CYAN);
                g2.fillRect(0, 0, 4, getHeight());
                // 1px separator at bottom
                g2.setColor(ACCENT_CYAN_DIM);
                g2.fillRect(0, getHeight() - 1, getWidth(), 1);
                g2.dispose();
            }
        };
        header.setOpaque(true);
        header.setBackground(new Color(0x00, 0x28, 0x40));
        header.setPreferredSize(new Dimension(0, 34));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(0, 14, 0, 0));
        header.add(titleLabel, BorderLayout.CENTER);

        panel.add(header,  BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    // ── HudBorder (kept for map wrapper only) ─────────────────────────────────

    /** Simple 1-px border with a subtle glow — used only for the map container. */
    public static class HudBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Outer glow
            g2.setColor(new Color(0x00, 0xD4, 0xFF, 20));
            g2.setStroke(new BasicStroke(3f));
            g2.drawRect(x, y, w - 1, h - 1);
            // Main 1-px border
            g2.setColor(BORDER_DIM);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRect(x, y, w - 1, h - 1);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c)                      { return new Insets(1, 1, 1, 1); }
        @Override public Insets getBorderInsets(Component c, Insets i)            { i.set(1,1,1,1); return i; }
    }
}
