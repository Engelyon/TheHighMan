package thehighman.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;

public class MaconhaMedicinal extends AbstractRelic {
    public static final String ID = "thehighman:MaconhaMedicinal";

    public MaconhaMedicinal() {
        super(ID, "maconha.png", RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return "Toda vez que você perder HP devido à Bad Trip, cure 2 de HP.";
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MaconhaMedicinal();
    }

    // Este método deve ser chamado pela lógica que aplica dano da Bad Trip
    public void onBadTripDano() {
        flash();
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
    }
}