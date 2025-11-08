package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class MordidaNoLanche extends BaseCard {
    public static final String ID = makeID("MordidaNoLanche");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int BASE_DAMAGE = 4;
    private static final int UPG_DAMAGE = 1;
    private static final int HITS = 3;
    private static final int COMIDO_THRESHOLD = 2;
    private static final int BONUS_PER_HIT = 1;

    public MordidaNoLanche() {
        super(ID, info);
        setDamage(BASE_DAMAGE, UPG_DAMAGE);
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = 0;

        if (p.hasPower(ComidoPower.POWER_ID) && p.getPower(ComidoPower.POWER_ID).amount >= COMIDO_THRESHOLD) {
            bonus = BONUS_PER_HIT;
        }

        for (int i = 0; i < HITS; i++) {
            addToBot(new DamageAction(m,
                    new DamageInfo(p, this.damage + bonus, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 4 → 5 de dano base por golpe
            initializeDescription();
        }
    }
}