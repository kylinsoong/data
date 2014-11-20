package org.apache.poi.hssf.eventusermodel.examples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class HSSFEventExample {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String otherholdings = "/home/kylin/project/teiid-samples/teiid-quickstart/src/file/otherholdings.xls";
		OutputHSSFListener outputlistener = new OutputHSSFListener();
		MissingRecordAwareHSSFListener misslistener = new MissingRecordAwareHSSFListener(outputlistener);
		FormatTrackingHSSFListener formatListener = new FormatTrackingHSSFListener(misslistener);
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(otherholdings));
		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();
		request.addListenerForAllRecords(formatListener);
		factory.processWorkbookEvents(request, fs);

	}
	
	/** So we known which sheet we're on */
	private static int sheetIndex = -1;
	private static BoundSheetRecord[] orderedBSRs;
	private static List<BoundSheetRecord> boundSheetRecords = new ArrayList<BoundSheetRecord>();
	
	static class OutputHSSFListener implements HSSFListener {
		
		public void processRecord(Record record) {
			
			switch (record.getSid()) {
			case BOFRecord.sid:
				BOFRecord br = (BOFRecord)record;
				if(br.getType() == BOFRecord.TYPE_WORKSHEET){
					sheetIndex++;
					if(orderedBSRs == null) {
						orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
						System.out.println(orderedBSRs[sheetIndex].getSheetname());
					}
				}
//			case BoundSheetRecord.sid:
//				BoundSheetRecord r = (BoundSheetRecord) record;
//				System.out.println(r.getSheetname());
			default:
				break;
			}
			
		}
		
	}

}
