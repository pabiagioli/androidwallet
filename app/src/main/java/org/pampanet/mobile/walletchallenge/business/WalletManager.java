package org.pampanet.mobile.walletchallenge.business;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;

public class WalletManager {

    private final NetworkParameters networkParameters;

    private Wallet wallet;
    private boolean isCreated;

    public WalletManager(NetworkParameters networkParameters){
        this.networkParameters = networkParameters;
        isCreated = false;
    }

    public void createWallet() throws BlockStoreException {
        wallet = new Wallet(networkParameters);
        final BlockChain blockChain = new BlockChain(networkParameters, wallet, new MemoryBlockStore(networkParameters));
        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addWallet(wallet);
        peerGroup.start();
        isCreated = true;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public String getWalletAddress(){
        Address a = wallet.currentReceiveAddress();
        return a.toBase58();
    }

    public String getPrivateKey(){
        ECKey b = wallet.currentReceiveKey();
        return b.getPrivKey().toString();
    }

    public String getNewAddressFromKey(){
        Address c = wallet.freshReceiveAddress();
        return c.toBase58();
    }


}
