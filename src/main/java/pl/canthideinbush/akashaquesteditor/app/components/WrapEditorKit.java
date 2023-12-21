package pl.canthideinbush.akashaquesteditor.app.components;

import javax.swing.text.*;

//Solution from answer: https://stackoverflow.com/a/72624094
public class WrapEditorKit extends StyledEditorKit {


    ViewFactory defaultFactory = new WrapColumnFactory();

    public ViewFactory getViewFactory() {
        return defaultFactory;
    }




    static class WrapColumnFactory implements ViewFactory {

        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                switch (kind) {
                    case AbstractDocument.ContentElementName -> {
                        return new WrapLabelView(elem);
                    }
                    case AbstractDocument.ParagraphElementName -> {
                        return new ParagraphView(elem);
                    }
                    case AbstractDocument.SectionElementName -> {
                        return new BoxView(elem, View.Y_AXIS);
                    }
                    case StyleConstants.ComponentElementName -> {
                        return new ComponentView(elem);
                    }
                    case StyleConstants.IconElementName -> {
                        return new IconView(elem);
                    }
                }
            }

            // default to text display
            return new LabelView(elem);
        }
    }

    static class WrapLabelView extends LabelView {

        public WrapLabelView(Element elem) {
            super(elem);
        }

        public float getMinimumSpan(int axis) {
            return switch (axis) {
                case View.X_AXIS ->
                        0;
                case View.Y_AXIS ->
                        super.getMinimumSpan(axis);
                default ->
                        throw new IllegalArgumentException("Invalid axis: " + axis);
            };
        }
    }



}
