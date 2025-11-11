package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

public class ViagemSoDeIda extends BaseCard {
    public static final String ID = makeID("ViagemSoDeIda");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int BASE_DAMAGE = 6;
    private static final int BONUS_DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public ViagemSoDeIda() {
        super(ID, info);
        setDamage(BASE_DAMAGE, UPG_DAMAGE);
        this.keywords.add("bad trip");
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int finalDamage = this.damage;

        // Verifica se BadTrip está atualmente na mão do jogador
        if (p != null && p.hand != null && p.hand.group != null &&
                p.hand.group.stream().anyMatch(card -> card.cardID.equals(makeID("BadTrip")))) {
            finalDamage += BONUS_DAMAGE;
        }

        addToBot(new DamageAction(m, new DamageInfo(p, finalDamage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.POISON));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 6 → 9 de dano base
            initializeDescription();
        }
    }
}