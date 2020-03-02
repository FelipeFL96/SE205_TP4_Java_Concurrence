public class BoundedBufferMain {

    public static void main (String[] args) {
        BoundedBuffer buffer;

        // Check the arguments of the command line
        if (args.length != 1){
            System.out.println ("PROGRAM FILENAME");
            System.exit(1);
        }
        Utils.init(args[0]);

        // Create a buffer
        if (Utils.sem_impl == 0)
            buffer = new NatBoundedBuffer(Utils.bufferSize);
        else
            buffer = new SemBoundedBuffer(Utils.bufferSize);

        // Create producers and then consumers
        Consumer consumers[] = new Consumer[ (int) Utils.nConsumers ];
        Producer producers[] = new Producer[ (int) Utils.nProducers ];

        for (int i = 0; i < Utils.nConsumers; i++) {
            consumers[i] = new Consumer(i, buffer);
            consumers[i].start();
        }

        for (int i = 0; i < (int) Utils.nProducers; i++) {
            producers[i] = new Producer((int) Utils.nConsumers + i, buffer);
            producers[i].start();
        }
    }
}
