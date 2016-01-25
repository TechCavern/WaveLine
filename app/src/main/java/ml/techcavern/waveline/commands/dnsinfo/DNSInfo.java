package ml.techcavern.waveline.commands.dnsinfo;

/**
 * Created by jzhou on 12/28/2015.
 */

import android.content.Context;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.xbill.DNS.AAAARecord;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.Type;

import java.util.ArrayList;
import java.util.List;

import ml.techcavern.waveline.annots.CMD;
import ml.techcavern.waveline.objects.Command;
import ml.techcavern.waveline.objects.OutputCard;
import ml.techcavern.waveline.utils.GeneralUtils;

@CMD
public class DNSInfo extends Command {

    public DNSInfo() {
        super(GeneralUtils.toArray("dnsinfo di dns"), "dnsinfo [domain]", "Looks up a domain for information");
    }

    @Override
    public void onCommand(Context context, String[] args) throws Exception {
        args = ArrayUtils.remove(args, 0);
        Resolver resolver = new SimpleResolver();
        String domain = args[0];
        domain = domain.replace("http://", "").replace("https://", "");
        Lookup lookup = new Lookup(domain, Type.ANY);
        lookup.setResolver(resolver);
        lookup.setCache(null);
        Record[] records = lookup.run();
        if (lookup.getResult() == Lookup.SUCCESSFUL) {
            List<String> results = new ArrayList<>();
            for (Record rec : records) {
                if (rec instanceof ARecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((ARecord) rec).getAddress().toString().replace("./", "/"));
                } else if (rec instanceof NSRecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((NSRecord) rec).getTarget().toString().replace("./", "/"));
                } else if (rec instanceof AAAARecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((AAAARecord) rec).getAddress().toString().replace("./", "/"));
                } else if (rec instanceof CNAMERecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((CNAMERecord) rec).getAlias() + " - " + ((CNAMERecord) rec).getTarget());
                } else if (rec instanceof TXTRecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + StringUtils.join(rec, " "));
                } else if (rec instanceof MXRecord) {
                    results.add("[" + Type.string(rec.getType()) + "] " + ((MXRecord) rec).getPriority() + " - " + ((MXRecord) rec).getTarget());
                } else if (rec instanceof SRVRecord) {
                }
            }
            GeneralUtils.addCard(new OutputCard(context, "DNS Info of " + args[0], StringUtils.join(results, "\n")));
        } else {
            GeneralUtils.addCard(new OutputCard(context, "Error", "Invalid Domain"));
        }
    }
}

