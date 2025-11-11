package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class Burgao extends BaseCard {
    public static final String ID = makeID("Burgao");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK_PER_COMIDO = 4;
    private static final int BLOCK_PER_COMIDO_UPG = 6;

    public Burgao() {
        super(ID, info);
        this.exhaust = true;
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p == null) {
            return;
        }
        int comidoAmount = 0;
        if (p.hasPower(ComidoPower.POWER_ID)) {
            comidoAmount = p.getPower(ComidoPower.POWER_ID).amount;
        }
        int per = upgraded ? BLOCK_PER_COMIDO_UPG : BLOCK_PER_COMIDO;
        int totalBlock = comidoAmount * per;
        if (totalBlock > 0) {
            addToBot(new GainBlockAction(p, p, totalBlock));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int comidoAmount = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(ComidoPower.POWER_ID)) {
            comidoAmount = AbstractDungeon.player.getPower(ComidoPower.POWER_ID).amount;
        }
        int per = upgraded ? BLOCK_PER_COMIDO_UPG : BLOCK_PER_COMIDO;
        int displayBlock = comidoAmount * per;
        this.baseBlock = displayBlock;
        this.block = displayBlock;
        this.isBlockModified = false;
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}