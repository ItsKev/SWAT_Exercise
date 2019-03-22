package ch.hslu.appe.fbs.model;

public final class SampleModel {

    public static void main(String[] args) {
        final DataTruncator dataTruncator = new DataTruncator();
        dataTruncator.deleteDataInEntities();
        final DataPopulator dataPopulator = new DataPopulator();
        dataPopulator.insertSampleDataIntoDB();
    }
}
