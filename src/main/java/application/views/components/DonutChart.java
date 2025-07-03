package application.views.components;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;

public class DonutChart extends Composite<Div> {

    public DonutChart(long erledigt, long ueberfaellig, long offen) {
        setClassName("donut-chart");

        long total = erledigt + ueberfaellig + offen;
        if (total == 0) total = 1;

        final int radius = 50;
        final int circumference = (int) (2 * Math.PI * radius);

        double erledigtPercent = (erledigt * 100.0) / total;
        double ueberfaelligPercent = (ueberfaellig * 100.0) / total;
        double offenPercent = (offen * 100.0) / total;

        int erledigtLength = (int) (circumference * (erledigtPercent / 100));
        int ueberfaelligLength = (int) (circumference * (ueberfaelligPercent / 100));
        int offenLength = (int) (circumference * (offenPercent / 100));

        int erledigtOffset = 0;
        int ueberfaelligOffset = erledigtLength;
        int offenOffset = erledigtLength + ueberfaelligLength;

        String svg = "<svg width='140' height='180' viewBox='0 0 140 180' style='display:block;margin:auto;'>" +
                // Hintergrund-Kreis (hellgrau)
                "<circle r='" + radius + "' cx='70' cy='70' fill='transparent' stroke='#ddd' stroke-width='20' />" +

                // Erledigt (grün)
                "<circle r='" + radius + "' cx='70' cy='70' fill='transparent' stroke='var(--primary-color)' stroke-width='20' " +
                "stroke-dasharray='" + erledigtLength + " " + (circumference - erledigtLength) + "' " +
                "stroke-dashoffset='" + (-erledigtOffset) + "' " +
                "transform='rotate(-90 70 70)' />" +

                // Überfällig (orange)
                "<circle r='" + radius + "' cx='70' cy='70' fill='transparent' stroke='var(--warn-color)' stroke-width='20' " +
                "stroke-dasharray='" + ueberfaelligLength + " " + (circumference - ueberfaelligLength) + "' " +
                "stroke-dashoffset='" + (-ueberfaelligOffset) + "' " +
                "transform='rotate(-90 70 70)' />" +

                // Offen (blau)
                "<circle r='" + radius + "' cx='70' cy='70' fill='transparent' stroke='var(--text-primary)' stroke-width='20' " +
                "stroke-dasharray='" + offenLength + " " + (circumference - offenLength) + "' " +
                "stroke-dashoffset='" + (-offenOffset) + "' " +
                "transform='rotate(-90 70 70)' />" +

                "</svg>";

        getElement().setProperty("innerHTML", svg);
    }
}
