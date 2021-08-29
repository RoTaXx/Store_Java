package com.company;

public class CashRegisterThread implements Runnable {
    private CashRegister cashRegister;

    public CashRegisterThread(CashRegister cashRegister) {
        this.cashRegister = cashRegister;
    }


    @Override
    public synchronized void run() {
        for (Client client: this.cashRegister.getLinedUpClients()){
            this.cashRegister.markGoods(client);
            this.cashRegister.sellMarkedGoods(client);
        }
    }
}
